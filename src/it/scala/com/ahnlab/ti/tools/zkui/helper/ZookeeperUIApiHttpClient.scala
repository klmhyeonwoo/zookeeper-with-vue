package com.ahnlab.ti.tools.zkui.helper

import com.ahnlab.ti.tools.zkui.dto.ClusterDTO
import com.google.gson.Gson
import com.twitter.finagle.Http
import com.twitter.finagle.http._
import com.twitter.util.Await

class ZookeeperUIApiHttpClient(host: String, port: Int) {
  private val client = Http.client.newService(s"$host:$port")
  private val gson = new Gson()

  def getChildren(clusterName: String, path: String): Response = {
    val api = s"/api/zkui/clusters/$clusterName/tree?path=$path"
    val request = Request(Method.Get, api).host(host)
    request.setContentTypeJson()

    Await.result(client(request))
  }

  def getNode(clusterName: String, path: String, meta: Boolean = false): Response = {
    val api = s"/api/zkui/clusters/$clusterName/node?path=$path&meta=$meta"
    val request = Request(Method.Get, api).host(host)
    request.setContentTypeJson()

    Await.result(client(request))
  }

  def setNode(clusterName: String, path: String, overwrite: Boolean = false, value: String): Response = {
    val api = s"/api/zkui/clusters/${clusterName}/node?path=${path}&overwrite=${overwrite}"
    val request = Request(Method.Post, api).host(host)
    request.contentType = "text/plain"
    request.contentString = value

    Await.result(client(request))
  }

  def addCluster(clusterDTO: ClusterDTO): Response = {
    val api = s"/api/zkui/clusters/"
    val request = Request(Method.Post, api).host(host)
    request.contentString = gson.toJson(clusterDTO)
    request.setContentTypeJson()

    Await.result(client(request))
  }

  def getClusters(clusterName: String): Response = {
    val api = s"/api/zkui/clusters/${clusterName}"
    val request = Request(Method.Get, api).host(host)

    Await.result(client(request))
  }

}
