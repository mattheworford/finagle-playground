package filter

import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.{Future, Return, Throw}
import com.typesafe.scalalogging.LazyLogging

class DebugFilter[Req, Rep](id: String) extends SimpleFilter[Req, Rep] with LazyLogging {

  override def apply(request: Req, service: Service[Req, Rep]): Future[Rep] = {
    val start = System.nanoTime()
    logger.debug(s"[$id] received request $request")

    service(request).respond {
      case Return(response) =>
        val elapsed = (System.nanoTime() - start) / 1e6
        logger.debug(s"[$id] request took $elapsed ms, response: $response")
      case Throw(exception) =>
        val elapsed = (System.nanoTime() - start) / 1e6
        logger.error(s"[$id] request took $elapsed ms, exception occurred", exception)
    }
  }
}