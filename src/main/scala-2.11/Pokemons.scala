/**
  * Created by vincentliu on 12/11/2016.
  */
//import dispatch.Defaults._
//import dispatch._
import java.net._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
import scala.concurrent._
import scala.concurrent.duration._
import play.api.libs.json._

import scala.io.Source._
import scala.util.{Failure, Success}

object Pokemons extends App{
  // Define sc object
  val conf = new SparkConf().
    setMaster("local").
    setAppName("Pokemons")
  val sc = new SparkContext(conf)
  // Initial RDD
  val pokeText = sc.textFile("/Users/vincentliu/Desktop/Courses_2016Fall/CSYE7200_Scala/Final Project/300k.csv")

  case class Coordinate(lat: Double, lng: Double)
  // Filter pokemon RDD belong to US
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
  // Due to multiple times of usage, persist this RDD
  usPokes.persist(StorageLevel.MEMORY_AND_DISK)
  // Build the coordinates RDD.
  val usCoordinates = usPokes.map(
    s => Coordinate(s(1).toDouble, s(2).toDouble)
  )
  usCoordinates.persist(StorageLevel.MEMORY_AND_DISK)
  //println(usCoordinates.count)

  def json(url: String): JsValue = Json.parse(fromURL(url).mkString)

  // Due to Google API request limit, need to split the RDD by 2500 as a subset
  val part1 = usCoordinates take 2500

  // Test on the first record and see what we can get through the reverse geocoding
  val coord = part1.take(1)
  val url_1 = s"https://maps.googleapis.com/maps/api/geocode/json?latlng=${coord(0).lat},${coord(0).lng}&key=AIzaSyCdGD2_7T1Bsi9dtp_dhgmL5p4gKTXrmUU"//  val req = url(url_1)
  val result = json(url_1)
  println(url_1)

//  val textWrapper = Http(req OK as.String)
//  val res = Await.result(textWrapper, 10.millisecond)
//  val text = for (str <- res) str

//  val text = textWrapper onSuccess {
//    case content => content
//  }

//  println(result)
//  val zip_1 = (result \ "results" \\ "formatted_address")(0).as[String].split(",")(2).split(" ")(2)
  val adds = (result \ "results" \\ "formatted_address")
  val pattern = "\\d{5}\\,\\s(USA)".r
  val zip_1 = for (pattern(zip, _) <- adds) zip
  println(zip_1)

//  val zips_1 = for {
//    coord <- part1
//    url = s"https://maps.googleapis.com/maps/api/geocode/json?latlng=${coord.lat},${coord.lng}&key=AIzaSyD2LzhM2EP71gtwaoL0tbyeeHbX80x0TAU"
//    res = json(url)
//    adds = res \ "results" \\ "formatted_address"
//    for
//  } yield zip
//
//  println(zips_1.size)
//  for (zip <- zips_1) println(zip)
}
