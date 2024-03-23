
import com.twitter.finagle.Http
import com.twitter.util.Await
import service.StringLengthService

object Main extends App {
  val service = StringLengthService.init()
  private val server = Http.serve(":9090", service)
  Await.ready(server)
}