ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "tools_zookeeper-ui"
  )

libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot-starter-web" % "2.4.13",
  "org.springframework.boot" % "spring-boot-starter-test" % "2.6.15" % Test,
  "org.junit.platform" % "junit-platform-launcher" % "1.10.1" % Test,
  "org.projectlombok" % "lombok" % "1.18.30" % "provided",
  "org.apache.zookeeper" % "zookeeper" % "3.9.1"
)

mainClass in (Compile, run) := Some("com.ahnlab.ti.tools.zkui.ZkUIApplication")