name := "leap-reveal"

scalaVersion := "2.11.0"

version := "0.1"

libraryDependencies += "me.shadaj.leap" %% "scala-leap" % "0.1"

fork := true

javaOptions := Seq("-Djava.library.path=/Users/shadaj/Software/LeapDeveloperKit/LeapSDK/lib")