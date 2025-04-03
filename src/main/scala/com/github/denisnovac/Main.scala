package com.github.denisnovac

import cats.syntax.all.*
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.circe.*
import io.circe.generic.auto.*
import sttp.apispec.openapi.circe.yaml.*
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.swagger.SwaggerUI
import cats.effect.IOApp
import cats.effect.ExitCode
import cats.effect.IO
import cats.Monad
import java.time.Instant
import com.github.denisnovac.blog.BlogEntry
import java.util.UUID
import com.github.denisnovac.blog.EntryMeta
import com.github.denisnovac.blog.NewEntry
import cats.effect.kernel.Ref

object Main extends IOApp:

  override def run(args: List[String]): IO[ExitCode] =
    for {
      inMemory <- Ref.of[IO, List[BlogEntry]](List.empty)
      server    = new Server[IO](inMemory)
      docs      = Endpoints.docs

      _ <- Http4s
             .of[IO](
               server.endpoints,
               docs
             )
             .use(_ => IO.never.as(ExitCode.Success))
    } yield ExitCode.Success

object Endpoints:

  val time =
    endpoint.get
      .tag("Clock")
      .in("time")
      .out(plainBody[String])

  val newEntry =
    endpoint.post
      .tag("Blog")
      .in("blog" / "entry")
      .in(jsonBody[NewEntry])
      .out(jsonBody[EntryMeta])

  val blogEntriesList =
    endpoint.get
      .tag("Blog")
      .in("blog" / "list")
      .out(jsonBody[List[EntryMeta]])

  val blogEntry =
    endpoint.get
      .tag("Blog")
      .in("blog" / "entry" / path[UUID]("id"))
      .out(jsonBody[Option[BlogEntry]])

  val endpoints =
    time ::
      blogEntry ::
      blogEntriesList ::
      newEntry ::
      Nil

  val docs =
    OpenAPIDocsInterpreter().toOpenAPI(endpoints, "Test spp", "0.0.1")

class Server[F[_]: Monad](ref: Ref[F, List[BlogEntry]]):

  val time =
    Endpoints.time
      .serverLogicSuccess { _ =>
        Monad[F].pure(Instant.now().toString())
      }

  val blogEntriesList =
    Endpoints.blogEntriesList
      .serverLogicSuccess { _ =>
        ref.get.map(list =>
          list.map(entry =>
            EntryMeta(
              entry.id,
              entry.title,
              entry.postedAt
            )
          )
        )

      }

  val newEntry =
    Endpoints.newEntry
      .serverLogicSuccess { entry =>
        val newEntry = BlogEntry(UUID.randomUUID(), entry.title, entry.text, Instant.now())

        ref.update(_ :+ newEntry).as(EntryMeta(newEntry.id, newEntry.title, newEntry.postedAt))
      }

  val blogEntry =
    Endpoints.blogEntry
      .serverLogicSuccess { id =>
        ref.get.map(_.find(_.id == id))
      }

  val endpoints =
    time ::
      blogEntry ::
      blogEntriesList ::
      newEntry ::
      Nil
