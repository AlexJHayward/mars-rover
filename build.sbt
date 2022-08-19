ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

run / connectInput := true

lazy val root = (project in file("."))
  .settings(
    name := "mars-rover"
  )
  .settings(
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.13" % Test
    )
  )
