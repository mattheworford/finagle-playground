package client

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Method, Request, Response}
import com.twitter.finagle.service.TimeoutFilter
import com.twitter.util.{Duration, Future, JavaTimer}

object TimeoutClient {
  def main(args: Array[String]): Unit = {
    val originalClient: Service[Request, Response] =
      Http.newService("localhost:9090")
    val timeoutFilter = new TimeoutFilter[Request, Response](
      Duration.fromSeconds(1),
      new JavaTimer(),
    )
    val filteredClient = timeoutFilter.andThen(originalClient)

    val request                    = Request(Method.Get, "/?name=matthew")
    val response: Future[Response] = filteredClient(request)

    response.onSuccess(resp => println(resp.getContentString()))
    response.onFailure(ex => ex.printStackTrace())
    Thread.sleep(2000)
  }
}
