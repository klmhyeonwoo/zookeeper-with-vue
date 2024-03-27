package com.ahnlab.ti.tools.zkui.controller

import com.ahnlab.ti.tools.zkui.dto.ClusterDTO
import com.ahnlab.ti.tools.zkui.helper.{Configs, ITHelper, ZookeeperUIApiHttpClient}
import com.ahnlab.ti.tools.zkui.util.zookeeper.ZookeeperAccessor
import io.circe.{Json, parser}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import org.scalatest.concurrent.Eventually
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


/**
 *  Zookeeper UI의 요구사항을 테스트합니다.
 *
 *  [OnePager] https://docs.ahnlab.com/display/ATIP/Zookeeper+UI+OnePager
 */
class ZookeeperControllerIT extends AnyFlatSpec with Matchers with BeforeAndAfterEach with BeforeAndAfterAll with Eventually {
  private val httpClient = new ZookeeperUIApiHttpClient(Configs.ServerHost, Configs.ServerPort)

  override def beforeAll(): Unit = {
    // [OnePager] 6.3.1 zookeeper 서버 등록
    addZookeeperCluster()
  }

  override def beforeEach(): Unit = ITHelper.insertMockDatas()

  override def afterEach(): Unit = ITHelper.deleteMockDatas()

  it should "[OnePager] 6.3.2 Path 조회" in {
    //When
    val response = httpClient.getChildren(Configs.ClusterName, "/node")

    //Then
    toJson(response.contentString) match {
      case Right(json) => json should be(
        toJson("""
                 |{
                 |   "child1": "value1",
                 |   "child2": "value2"
                 |}
                 |""".stripMargin).getOrElse(fail("JSON parsing failed"))
      )
      case Left(error) => fail(s"$error")
    }

  }

  it should "[OnePager] 6.3.3 Path 추가" in {
    //Given
    val path = "/test1/test2"
    val value = "value"

    //When
    val response = httpClient.setNode(Configs.ClusterName, path, value = value)

    //Then
    toJson(response.contentString) match {
      case Right(json) => json should be(
        toJson("""
                 |{
                 |   "path": "/test1/test2",
                 |   "value": "value"
                 |}
                 |""".stripMargin).getOrElse(fail("JSON parsing failed"))
      )
      case Left(error) => fail(s"$error")
    }

    //Mock 데이터 삭제
    val zk = new ZookeeperAccessor().getConnection(Configs.DaZkHosts)
    zk.delete(path, -1)
    zk.delete("/test1", -1)
  }

  //metadata 는 저장 시간 등이 있기 때문에, 데이터를 정확하게 파악할 수 없다.
  //그렇기 때문에, 테스트 시에 value 값만 테스트 하도록 한다.
  it should "[OnePager] 6.3.4 metadata 조회" in {
    //When
    val response = httpClient.getNode(Configs.ClusterName, "/node/child1", meta = false)

    //Then
    toJson(response.contentString) match {
      case Right(json) => json should be(
        toJson("""
                 |{
                 |   "value": "value1"
                 |}
                 |""".stripMargin).getOrElse(fail("JSON parsing failed"))
      )
      case Left(error) => fail(s"$error")
    }

  }

  private def toJson(str: String): Either[io.circe.Error, Json] = parser.parse(str)

  private def addZookeeperCluster() : Unit = {
    val clusterDTO = new ClusterDTO
    clusterDTO.setHost(Configs.DaZkHosts)
    clusterDTO.setName(Configs.ClusterName)
    httpClient.addCluster(clusterDTO)
    println("add cluster...")
  }

}
