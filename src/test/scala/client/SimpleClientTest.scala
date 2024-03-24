package client

import com.twitter.finagle.{Service, http}
import com.twitter.util.{Await, Future}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._

class SimpleClientTest extends AnyFlatSpec with Matchers {

  "SimpleClient" should "return response content when request is successful" in {
    val mockService  = mock(classOf[Service[http.Request, http.Response]])
    val mockResponse = mock(classOf[http.Response])
    when(mockResponse.getContentString()).thenReturn("Hello, matthew!")
    when(mockService.apply(any[http.Request]))
      .thenReturn(Future.value(mockResponse))

    val client   = new SimpleClient(mockService)
    val response = Await.result(client.sendRequest("/?string=matthew"))

    response.getContentString() should be("Hello, matthew!")
  }

  it should "throw exception when request fails" in {
    val mockService = mock(classOf[Service[http.Request, http.Response]])
    when(mockService.apply(any[http.Request]))
      .thenReturn(Future.exception(new Exception("Request failed")))

    val client = new SimpleClient(mockService)

    a[Exception] should be thrownBy {
      Await.result(client.sendRequest("/?string=matthew"))
    }
  }

  it should "handle empty path parameter" in {
    val mockService  = mock(classOf[Service[http.Request, http.Response]])
    val mockResponse = mock(classOf[http.Response])
    when(mockResponse.getContentString()).thenReturn("Hello, !")
    when(mockService.apply(any[http.Request]))
      .thenReturn(Future.value(mockResponse))

    val client   = new SimpleClient(mockService)
    val response = Await.result(client.sendRequest(""))

    response.getContentString() should be("Hello, !")
  }
}
