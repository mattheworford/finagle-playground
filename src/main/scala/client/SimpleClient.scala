package client

import com.twitter.finagle.http.{Method, Request, Response}
import com.twitter.finagle.{Http, Service}
import com.twitter.util.Future

object SimpleClient {
  def main(args: Array[String]): Unit = {
    val client: Service[Request, Response] = Http.newService("localhost:9090")

    val request = Request(Method.Get, "/?name=matthew")
    val response: Future[Response] = client(request)

    response.onSuccess(resp => println(resp.getContentString()))
    response.onFailure(ex => ex.printStackTrace())
  }
}