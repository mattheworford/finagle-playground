import com.twitter.finagle.Http
import com.twitter.util.Await
import filter.DebugFilter
import service.StringLengthService

object Main extends App {
  private val stringLengthService = StringLengthService.init()
  private def simpleHttpServer(port: Int) =
    Http.serve(
      s":$port",
      new DebugFilter(s"server-$port").andThen(stringLengthService),
    )

  (9089 to 9091)
    .map { port =>
      simpleHttpServer(port)
    }
    .foreach(server => Await.ready(server))
}
