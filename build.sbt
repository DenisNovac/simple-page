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
        "com.softwaremill.sttp.tapir" %% "tapir-core",
        "com.softwaremill.sttp.tapir" %% "tapir-http4s-server",
        "com.softwaremill.sttp.tapir" %% "tapir-json-circe",
        "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs",
        "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle"
      ).map(_ % tapirVersion)
    )
    .settings(
      libraryDependencies ++= Seq(
        "com.softwaremill.sttp.apispec" %% "openapi-circe-yaml"  % "0.11.7",
        "io.circe"                      %% "circe-generic"       % "0.14.12",
        "org.http4s"                    %% "http4s-ember-server" % http4sVersion,
        "org.http4s"                    %% "http4s-dsl"          % http4sVersion
      )
    )
    .settings(
      libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.5.18"
    )
