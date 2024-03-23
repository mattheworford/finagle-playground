package service

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http
import com.twitter.util.{Await, Future}

object StringLengthService {
  def init(): Service[http.Request, http.Response] = {
    new Service[http.Request, http.Response] {
      def apply(req: http.Request): Future[http.Response] = {
        val name = req.getParam("name")
        val response = http.Response(req.version, http.Status.Ok)
        response.setContentString(name.length.toString)
        Future.value(response)
      }
    }
  }
}