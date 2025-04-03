package com.github.denisnovac.blog

import io.circe.generic.semiauto._
import io.circe.Codec

import java.time.Instant
import sttp.tapir.Schema
import java.util.UUID

final case class EntryMeta(id: UUID, title: String, postedAt: Instant)

object EntryMeta {
  implicit val codec: Codec[EntryMeta]   = deriveCodec
  implicit val schema: Schema[EntryMeta] = Schema.derived
}
