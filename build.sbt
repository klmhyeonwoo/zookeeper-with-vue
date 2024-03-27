import com.ahnlab.asd.build.LibVersion
import sbt.Keys._
import scala.language.postfixOps
import scala.sys.process._

val springBootVersion = "2.7.12"
val finchVersion = "0.31.0"
val circeVersion = "0.13.0"
val circeOpticsVersion = "0.13.0"
val jacksonVersion = "2.13.2"

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .enablePlugins(JavaServerAppPackaging)
  .settings(AsdBuildPlugin.commonSettings: _*)
  .enablePlugins(SystemdPlugin)
  .settings(AsdBuildPlugin.helmPublishSettings: _*)
  .enablePlugins(HelmPlugin, HelmPublishPlugin)
  .settings(
    organization := "com.ahnlab.ti",
    name := "ti-zookeeper-ui",
    version := "1.0.0-SNAPSHOT",
    //docker 경로 설정
    prjName := "ti",
    dockerRepoName := "atip",
    libraryDependencies ++= Seq(
      "com.ahnlab.asd" %% "asd-util-common" % "1.6.1.1" exclude ("com.google.code.gson", "gson"),
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.13.2.2","com.fasterxml.jackson.core" % "jackson-core" % jacksonVersion,
      "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonVersion,
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.13.2.2",
      "com.fasterxml.jackson.module" % "jackson-module-paranamer" % jacksonVersion,
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
      "com.google.code.gson" % "gson" % "2.8.6",
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-generic-extras" % circeVersion,
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "io.circe" %% "circe-optics" % circeOpticsVersion,
      "org.scalatest" %% "scalatest" % "3.2.17" % "test,it",
      "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion,
      "org.springframework.boot" % "spring-boot-starter-test" % springBootVersion % Test,
      "org.springframework.boot" % "spring-boot-starter-validation" % springBootVersion,
      "org.junit.platform" % "junit-platform-launcher" % "1.10.1" % Test,
      "org.projectlombok" % "lombok" % "1.18.30" % "provided",
      "org.apache.zookeeper" % "zookeeper" % "3.9.1",
      "ch.qos.logback" % "logback-classic" % "1.2.9",
    ),
    /** [[SystemdPlugin]]을 사용하는 경우 [[killTimeout]]은 기본적으로 5로 설정된다.
     * 자연스럽게 TimeoutStopSec 설정 역시 5로 설정된다.
     * consumer에 대한 종료 처리 등을 고려하여 해당 값을 60초로 설정한다.
     */
    killTimeout := 60,
    linuxScriptReplacements += ("TimeoutStopSec" -> killTimeout.value.toString),
    libraryDependencies ~= { _.map(_.exclude("org.slf4j", "slf4j-log4j12")) },
    dependencyOverrides ++= Seq(
      // netty-all 와 netty-common, netty-handler 등의 개별 netty 라이브러리간의 버전 차이로 인하여
      // 실행 오류가 발생하여 명시적으로 netty-all 라이브러리의 버전을 상위 버전인 개별 netty 라이브러리의 버전과 동일하게 고정함.
      "io.netty" % "netty-all" % LibVersion.nettyVersion,
    ),
    /** Add scalac options for more warning messages
     *
     * -deprecation
     *   Emit warning and location for usages of deprecated APIs.
     *   Available since Scala version 2.2.1
     *
     * -unchecked
     *   Enable detailed unchecked (erasure) warnings
     *   Non variable type-arguments in type patterns are unchecked since they are eliminated by erasure
     *   Available since Scala version 2.3.0
     *
     * -feature
     *   Emit warning and location for usages of features that should be imported explicitly.
     */
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    updateOptions := updateOptions.value.withLatestSnapshots(false),
    // G1GC 및 Metaspace의 LargePage 사용
    Universal / javaOptions ++= Seq(
      "-J-XX:+UseG1GC",
      "-J-XX:+UseLargePagesInMetaspace",
      "-J-Dfile.encoding=UTF-8"
    ),
    publishHelm := {
      "curl -u%s:%s -XPUT https://%s/artifactory/%s/%s/%s/ -T %s/%s-%s.tgz"
        .format(
          AsdBuildPlugin.repoUser,
          AsdBuildPlugin.repoPass,
          AsdBuildPlugin.repoUrl,
          AsdBuildPlugin.helmLocalRepos,
          "ti",
          name.value,
          target.value,
          name.value,
          version.value
        ) !
    }
  )
 