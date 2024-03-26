package service

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.util.{Await, Future}
import filter.DebugFilter

object StringLengthService extends App {
  def init(): Service[Request, Response] = { (req: Request) =>
    val nameOption = Option(req.getParam("string"))
    nameOption match {
      case Some(name) =>
        Future.value(createResponse(req, name.length.toString, Status.Ok))
      case None =>
        Future.value(
          createResponse(req, "Missing 'string' parameter", Status.BadRequest)
        )
    }
  }
  private def createResponse(
    req: Request,
    content: String,
    status: Status,
  ): Response = {
    val response = Response(req.version, status)
    response.setContentString(content)
    response
  }
  private def simpleHttpServer(port: Int) =
    Http.serve(
      s":$port",
      new DebugFilter(s"server-$port").andThen(init()),
    )

  (9089 to 9091)
    .map { port =>
      simpleHttpServer(port)
    }
    .foreach(server => Await.ready(server))
}
