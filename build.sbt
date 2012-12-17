/* basic project info */
name := "ciphersaber"

organization := "org.saegesser"

version := "0.1.0"

description := "A Scala implementation of CipherSaber-2"

homepage := Some(url("https://github.com/MarcSaegesser/ciphersaber"))

startYear := Some(2012)

licenses := Seq(
  ("Apache Version 2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))
)

scmInfo := Some(
  ScmInfo(
    url("https://github.com/MarcSaegesser/ciphersaber"),
    "scm:git:https://github.com/MarcSaegesser/ciphersaber.git",
    Some("scm:git:git@github.com:MarcSaegesser/ciphersaber.git")
  )
)

// organizationName := "My Company"

/* scala versions and options */
scalaVersion := "2.9.2"

// crossScalaVersions := Seq("2.9.1")

offline := false

scalacOptions ++= Seq("-deprecation", "-unchecked")

// scalacOptions ++= Seq("-Ydependent-method-types") // if using shapeless

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

/* entry point */
mainClass in (Compile, packageBin) := Some("org.saegesser.ciphersaber.CipherSaber")

mainClass in (Compile, run) := Some("org.saegesser.ciphersaber.CipherSaber")

// CONTINUATIONS
// autoCompilerPlugins := true
// addCompilerPlugin("org.scala-lang.plugins" % "continuations" % "2.9.2")
// scalacOptions += "-P:continuations:enable"

//libraryDependencies += "com.github.scopt" %% "scopt" % "2.1.0"
/* dependencies */
libraryDependencies ++= Seq (
  "com.github.scopt" %% "scopt" % "2.1.0",
  "org.scalatest" %% "scalatest" % "2.0.M4" % "test"
)

//resolvers += "sonatype-public" at "https://oss.sonatype.org/content/groups/public"
/* you may need these repos */
resolvers ++= Seq(
  Resolver.sonatypeRepo("public")
  // Resolver.sonatypeRepo("snapshots")
  // Resolver.typesafeIvyRepo("snapshots")
  // Resolver.typesafeIvyRepo("releases")
  // Resolver.typesafeRepo("releases")
  // Resolver.typesafeRepo("snapshots")
  // JavaNet2Repository,
  // JavaNet1Repository,
)

// ivyXML := <dependencies>
//             <exclude module="logback-classic" />
//           </dependencies>

/* testing */
parallelExecution in Test := false

// testOptions += Tests.Argument(TestFrameworks.Specs2, "console", "junitxml")

// parallelExecution in Global := false //no parallelism between subprojects

/* sbt behavior */
logLevel in compile := Level.Warn

traceLevel := 5

/* publishing */
publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) Some(
    "snapshots" at nexus + "content/repositories/snapshots"
  )
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

mappings in (Compile, packageBin) ~= { (ms: Seq[(File, String)]) =>
  ms filter { case (file, toPath) =>
      toPath != "application.conf"
  }
}

publishArtifact in Test := false

// publishArtifact in (Compile, packageDoc) := false

// publishArtifact in (Compile, packageSrc) := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <developers>
    <developer>
      <id>MarcS</id>
      <name>Marc Saegesser</name>
      <email>marc@saegesser.org</email>
      <!-- <url></url> -->
    </developer>
  </developers>
)

// Josh Suereth's step-by-step guide to publishing on sonatype
// httpcom://www.scala-sbt.org/using_sonatype.html

/* assembly plugin */
assemblySettings

mainClass in AssemblyKeys.assembly := Some("org.saegesser.ciphersaber.CipherSaber")

test in AssemblyKeys.assembly := {}
