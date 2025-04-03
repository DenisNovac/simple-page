package com.github.denisnovac.blog

import io.circe.generic.semiauto._
import io.circe.Codec

import java.time.Instant
import sttp.tapir.Schema
import java.util.UUID

final case class BlogEntry(id: UUID, title: String, text: String, postedAt: Instant)

object BlogEntry {
  implicit val codec: Codec[BlogEntry]   = deriveCodec
  implicit val schema: Schema[BlogEntry] = Schema.derived
}
