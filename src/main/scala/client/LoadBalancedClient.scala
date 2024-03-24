package client

import com.twitter.finagle.http.{Method, Request}
import com.twitter.finagle.{Address, Http, Name}
import com.twitter.util.Future

object LoadBalancedClient {
  def main(args: Array[String]): Unit = {
    val addresses =
      (9089 to 9091).toList.map(port => Address("localhost", port))
    val name: Name = Name.bound(addresses: _*)
    val client     = Http.newService(name, "client")
    val requests =
      (1 to 20).map(_ => Request(Method.Get, "/"))
    val responses = requests.map(req => client(req).map(_.getContentString))

    Future.collect(responses).foreach(println)
    Thread.sleep(5000)
  }
}
