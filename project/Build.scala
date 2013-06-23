import sbt._
import Keys._
import play.Project._
import sbt.Defaults._
// imports standard command parsing functionality
import complete.DefaultParsers._



object ApplicationBuild extends Build {

  import Dependencies._

  val appName         = "kurad_app"
  val appVersion      = "0.1.2"
  val releases        = "/Users/harakazuhiro/gitrepo/maven-repo/release"
  val snapshot        = "/Users/harakazuhiro/gitrepo/maven-repo/snapshots"
  val mavenRepository = if (appVersion.endsWith("SNAPSHOT")) snapshot else releases

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean

  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
   // Add your own project settings here      
    publishTo := Some(Resolver.file("maven-repo", file(mavenRepository)))
  ).aggregate(SbtPluginProject)

  lazy val SbtPluginProject = Project(
    "kurad",
    file("kurad"),
    settings = Defaults.defaultSettings ++ Seq(
      sbtPlugin := true,
      version := "0.1.2",
      organization := "jp.greative",
      publishMavenStyle := true,
      publishTo := Some(Resolver.file("maven-repo", file(mavenRepository))),
      libraryDependencies := sbtDependencies
      //,
      //commands ++= Seq(jp.greative.kurad.KuradPlugin.kuradBasic)

    )
  )
  object Dependencies {

      val sbtDependencies = Seq(
          "play" % "play_2.10" % "2.1.1"
          ,
          "com.novocode" % "junit-interface" % "0.8" % "test"
          ,
          "junit" % "junit" % "3.8.1" % "test"
          ,
          "com.github.spullara.mustache.java" % "compiler" % "0.8.8"
          ,
          "org.avaje.ebeanorm"                %    "avaje-ebeanorm"           %   "3.1.2"



      )
      val buildScalaVersion = "2.10.0"

  }

}
