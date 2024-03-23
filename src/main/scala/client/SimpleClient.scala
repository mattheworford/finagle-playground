package client

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http
import com.twitter.util.{Await, Future}

object SimpleClient {
  def main(args: Array[String]): Unit = {
    val client: Service[http.Request, http.Response] = Http.newService("localhost:9090")

    val request = http.Request(http.Method.Get, "/?name=matthew")
    val response: Future[http.Response] = client(request)

    response.onSuccess(resp => println(resp.getContentString()))
    response.onFailure(ex => ex.printStackTrace())
  }
}