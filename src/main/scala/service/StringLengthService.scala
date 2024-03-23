package service

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http
import com.twitter.util.{Await, Future}

object StringLengthService {
  def init(): Service[Request, Response] = {
    new Service[Request, Response] {
      def apply(req: Request): Future[Response] = {
        val name = req.getParam("name")
        val response = Response(req.version, Status.Ok)
        response.setContentString(name.length.toString)
        Future.value(response)
      }
    }
  }
}