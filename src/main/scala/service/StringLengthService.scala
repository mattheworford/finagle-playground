package service

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.util.Future

object StringLengthService {
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
}
