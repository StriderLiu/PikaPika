/**
  * Created by vincentliu on 13/11/2016.
  */
import dispatch._, Defaults._
import scala.util.{Success, Failure}

object DispatchTest {
  def main (args: Array[String]) {
    val svc = url("https://maps.googleapis.com/maps/api/geocode/json?latlng=42.267324,-71.114623&key=AIzaSyCdGD2_7T1Bsi9dtp_dhgmL5p4gKTXrmUU");
    val response : Future[String] = Http(svc OK as.String)

    response onComplete {
      case Success(content) => {
        println("Successful response" + content)
      }
      case Failure(t) => {
        println("An error has occurred: " + t.getMessage)
      }
    }
  }
}
