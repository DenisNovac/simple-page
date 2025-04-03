package com.github.denisnovac.blog

import io.circe.generic.semiauto._
import io.circe.Codec

import java.time.Instant
import sttp.tapir.Schema
import java.util.UUID

final case class NewEntry(title: String, text: String)

object NewEntry {
  implicit val codec: Codec[NewEntry]   = deriveCodec
  implicit val schema: Schema[NewEntry] = Schema.derived
}
