lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      scalaVersion := "2.13.12",
      version      := "1.0"
    )),
    name := "finagle-playground",
    libraryDependencies ++= Seq(
      "com.twitter" %% "finagle-core" % "23.11.0",
      "com.twitter" %% "finagle-http" % "23.11.0"
    )
  )
