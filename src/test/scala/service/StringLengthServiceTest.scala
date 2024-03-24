package service

import com.twitter.finagle.http.{Request, Status}
import com.twitter.util.Await
import org.mockito.Mockito._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class StringLengthServiceTest extends AnyFlatSpec with Matchers {

  "StringLengthService" should "return string length when string is provided" in {
    val mockRequest = mock(classOf[Request])
    when(mockRequest.getParam("string")).thenReturn("matthew")

    val response = StringLengthService.init().apply(mockRequest)

    Await.result(response).getContentString() should be("7")
  }

  it should "return 0 when empty string is provided" in {
    val mockRequest = mock(classOf[Request])
    when(mockRequest.getParam("string")).thenReturn("")

    val response = StringLengthService.init().apply(mockRequest)

    Await.result(response).getContentString() should be("0")
  }

  it should "return a BadRequest status when no string is provided" in {
    val mockRequest = mock(classOf[Request])
    when(mockRequest.getParam("string")).thenReturn(null)

    val response = StringLengthService.init().apply(mockRequest)

    Await.result(response).status should be(Status.BadRequest)
  }
}
