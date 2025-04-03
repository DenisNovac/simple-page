package com.github.denisnovac

import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.circe.*
import io.circe.generic.auto.*
import sttp.apispec.openapi.circe.yaml.*
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import cats.effect.IOApp
import cats.effect.ExitCode
import cats.effect.IO
import cats.Monad
import java.time.Instant

object Main extends IOApp:

  override def run(args: List[String]): IO[ExitCode] =
    val server = new Server[IO]

    Http4s.of[IO](server.endpoints).use(_ => IO.never.as(ExitCode.Success))

object Endpoints:
  val time =
    endpoint.get
      .in("time")
      .out(plainBody[String])

  val endpoints =
    time ::
      Nil

  val docs =
    OpenAPIDocsInterpreter().toOpenAPI(endpoints, "Test spp", "0.0.1")

class Server[F[_]: Monad]():

  val time =
    Endpoints.time
      .serverLogicSuccess { _ =>
        Monad[F].pure(Instant.now().toString())
      }

  val endpoints =
    time ::
      Nil
