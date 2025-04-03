ThisBuild / scalaVersion := "3.6.4"

val tapirVersion  = "1.11.21"
val http4sVersion = "0.23.30"

lazy val app =
  (project in file("."))
    .settings(
      name         := "simple-page",
      organization := "com.github.denisnovac",
      version      := "0.1.0-SNAPSHOT"
    )
    .settings(
      libraryDependencies ++= Seq(
        "tapir-core",
        "tapir-http4s-server",
        "tapir-json-circe",
        "tapir-openapi-docs"
      ).map("com.softwaremill.sttp.tapir" %% _)
        .map(_ % tapirVersion) :+
        "com.softwaremill.sttp.apispec" %% "openapi-circe-yaml" % "0.11.7"
    )
    .settings(
      libraryDependencies ++= Seq(
        "org.http4s" %% "http4s-ember-server" % http4sVersion,
        "org.http4s" %% "http4s-dsl"          % http4sVersion
      )
    )
    .settings(
      libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.5.18"
    )
