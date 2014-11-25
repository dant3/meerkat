organization := "com.github.dant3"

name := "meerkat"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
    "org.scalatest"  %% "scalatest"  % "2.0.M8" % "test",
    "org.scalacheck" %% "scalacheck" % "1.10.1" % "test"
)
