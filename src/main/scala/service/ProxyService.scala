package service

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Await
import filter.DebugFilter

object ProxyService extends App {
  private val client: Service[Request, Response] =
    Http.newService("twitter.com:80")
  private def simpleHttpServer(port: Int) =
    Http.serve(
      s":$port",
      new DebugFilter(s"server-$port").andThen(client),
    )

  (9089 to 9091)
    .map { port =>
      simpleHttpServer(port)
    }
    .foreach(server => Await.ready(server))
}
