ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

lazy val root = (project in file("."))
  .settings(
    name := "lab01"
  )


libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.2.11"
