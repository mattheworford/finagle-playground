
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http
import com.twitter.util.{Await, Future}
import service.StringLengthService

object Main extends App {
  val service = StringLengthService.init()
  val server = Http.serve(":9090", service)
  Await.ready(server)
}