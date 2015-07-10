import sbt._
import sbt.Keys._

object HelloBuild extends Build {
    lazy val root = Project(id = "spray", base = file("."))
    		.settings(basicSettings: _*)
    		.settings(libraryDependencies ++= Dependencies.spray ++ Dependencies.akka ++ Dependencies.scala)

    lazy val basicSettings = Seq(
        scalaVersion := "2.11.2",
        resolvers ++= Seq(
            //"Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases",
            "spray" at "http://repo.spray.io"
            )
        )
}

object Dependencies {
    val SPRAY_VERSION = "1.3.1"
    val AKKA_VERSION = "2.3.11"
        
    val scala = Seq(
    			"org.scala-lang.modules" %% "scala-xml" % "1.0.2"
    		)
        
    val spray = Seq(
            	"io.spray" %% "spray-routing" % SPRAY_VERSION,
            	"io.spray" %% "spray-can" % SPRAY_VERSION
            )
    val akka = Seq(
            	"com.typesafe.akka" %% "akka-actor" % AKKA_VERSION
            )
}