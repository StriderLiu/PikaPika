name := "PikaPika"

version := "1.0"

scalaVersion := "2.11.8"

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.play" % "play-json_2.11" % "2.5.1",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.apache.spark" % "spark-core_2.11" % "2.0.1",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.7.2",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.7.2",
  "net.databinder.dispatch" % "dispatch-core_2.11" % "0.11.3"
)