package client

import com.twitter.finagle.http.{Method, Request, Response}
import com.twitter.finagle.{Http, Service}
import com.twitter.util.Future

class SimpleClient(service: Service[Request, Response]) {
  def sendRequest(path: String): Future[Response] = {
    val request  = Request(Method.Get, path)
    val response = service(request)
    response.onSuccess(resp => println(resp.getContentString()))
    response.onFailure(ex => ex.printStackTrace())
  }
}
object SimpleClient {
  def main(args: Array[String]): Unit = {
    val service: Service[Request, Response] = Http.newService("localhost:9090")
    val client                              = new SimpleClient(service)
    client.sendRequest("/?string=matthew")
  }
}
