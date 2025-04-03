package com.github.denisnovac

import sttp.tapir.server.http4s.Http4sServerOptions
import sttp.tapir.server.interceptor.cors.CORSConfig.AllowedOrigin
import sttp.tapir.server.interceptor.cors.{CORSConfig, CORSInterceptor}
import sttp.tapir.server.interceptor.log.DefaultServerLog
import org.http4s.HttpRoutes
import org.http4s.ember.server.EmberServerBuilder
import cats.effect.kernel.Async
import com.comcast.ip4s.{Host, Port}
import org.http4s.server.Router

import scala.concurrent.duration.*
import cats.effect.kernel.Resource

import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.server.ServerEndpoint
import sttp.apispec.openapi.OpenAPI
import sttp.tapir.swagger.SwaggerUI
import sttp.apispec.openapi.circe.yaml.*

object Http4s:
  def of[F[_]: Async](
      tapirRoutes: List[ServerEndpoint[Any, F]],
      docs: OpenAPI
  ): Resource[F, org.http4s.server.Server] =

    val host = "localhost"
    val port = 8081

    val allowedOriginsConfig =
      CORSConfig.default.allowAllOrigins

    val corsSettings =
      CORSInterceptor
        .customOrThrow[F](
          allowedOriginsConfig.allowAllHeaders.allowAllMethods
        )

    val options =
      Http4sServerOptions
        .customiseInterceptors[F]
        .corsInterceptor(corsSettings)
        .options

    val routes = Http4sServerInterpreter[F](options).toRoutes(tapirRoutes ++ SwaggerUI[F](docs.toYaml))

    val router = Router.apply[F]("/" -> routes).orNotFound

    EmberServerBuilder
      .default[F]
      .withHost(Host.fromString(host).get)
      .withPort(Port.fromInt(port).get)
      .withHttpApp(router)
      .withShutdownTimeout(5.seconds)
      .build
