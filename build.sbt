//import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys

// Turn this project into a Scala.js project by importing these settings
enablePlugins(ScalaJSPlugin)

name := "SideScroller"

scalaVersion := "2.11.6"

version := "0.1-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.0"
)

// Specify additional .js file to be passed to package-js and optimize-js
//unmanagedSources in (Compile, ScalaJSKeys.packageJS) +=
//    baseDirectory.value / "js" / "startup.js"

scalacOptions ++= Seq({
  s"-P:scalajs:mapSourceURI:file://${baseDirectory.value}->/"
})

//println(baseDirectory.value)