/**
  * Created by vincentliu on 12/11/2016.
  */
import dispatch.Defaults._
import dispatch._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
import play.api.libs.json._

import scala.io.Source._

object Pokemons extends App{
  val conf = new SparkConf().
    setMaster("local").
    setAppName("Pokemons")
  val sc = new SparkContext(conf)

  val pokeText = sc.textFile("/Users/vincentliu/Desktop/Courses_2016Fall/CSYE7200_Scala/Final Project/300k.csv")

  case class Coordinate(lat: Double, lng: Double)

  val usPokes = pokeText.map(_.split(",")).filter(s =>
    s(21) == "New_York" ||
    s(21) == "Los_Angeles" ||
    s(21) == "Chicago" ||
    s(21) == "Phoenix" ||
    s(21) == "Denver" ||
    s(21) == "Indianapolis" ||
    s(21) == "Detroit" ||
    s(21) == "Boise" ||
    s(21) == "Louisville" ||
    s(21) == "Monrovia")

  usPokes.persist(StorageLevel.MEMORY_AND_DISK)

  val usCoordinates = usPokes.map(
    s => Coordinate(s(1).toDouble, s(2).toDouble)
  )
  usCoordinates.persist(StorageLevel.MEMORY_AND_DISK)
  println(usCoordinates.count)

  val part1 = sc.parallelize(usCoordinates take 2500)

  def json(url: String): JsValue = Json.parse(fromURL(url).mkString)

  val coord = part1.take(1)
  val url_1 = s"https://maps.googleapis.com/maps/api/geocode/json?latlng=${coord(0).lat},${coord(0).lng}&key=AIzaSyCdGD2_7T1Bsi9dtp_dhgmL5p4gKTXrmUU"
  val req = url(url_1)
  println(url_1)

  val textWrapper = Http(req OK as.String)
  val text = for(txt <- textWrapper) txt
  println(text)
//  val zip_1 = (Json.parse(text)) \ "results" \ "address_components" \\ "long_name"
//  println(zip_1)
}
