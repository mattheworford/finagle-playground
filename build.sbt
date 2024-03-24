lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      scalaVersion := "2.13.12",
      version      := "1.0",
    )
  ),
  name := "finagle-playground",
  libraryDependencies ++= Seq(
    "com.twitter"                %% "finagle-core"    % "23.11.0",
    "com.twitter"                %% "finagle-http"    % "23.11.0",
    "org.scalatest"              %% "scalatest"       % "3.2.9"  % Test,
    "org.mockito"                 % "mockito-core"    % "5.10.0" % Test,
    "ch.qos.logback"              % "logback-classic" % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging"   % "3.9.5",
  ),
)
