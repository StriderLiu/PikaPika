package geo

/**
  * Created by vincentliu on 12/11/2016.
  */
import java.io._

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
import play.api.libs.json._

import scala.io.Source._
import scala.util.matching._

object Pokemons extends App{
  // Define sc object
  val conf = new SparkConf().
    setMaster("local").
    setAppName("Pokemons")
  val sc = new SparkContext(conf)
  // Initial RDD
  val pokeText = sc.textFile("/Users/Shuxian/Documents/CSYE7200-Fall2016/predictemall/300k.csv")

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

  // get json with url link
  def toJson(url: String): JsValue = Json.parse(fromURL(url).mkString)

  // retrieve zipcodes from json result
  def getZipCodes(data: Array[Coordinate], key: String, pattern: Regex) = for {
    coord <- data
    url = s"https://maps.googleapis.com/maps/api/geocode/json?latlng=${coord.lat},${coord.lng}&key=${key}"
    adds = (toJson(url) \ "results" \\ "formatted_address").toList.map(_.as[String])
    goodPart = for (pattern(_, state_zip, _) <- adds) yield state_zip
    zip = if (goodPart.isEmpty) "00000" else goodPart(0).split(" ")(1)
  } yield zip

  val pattern = """(.*)\,\s([A-Z]*\s\d{5})\,\s(USA)""".r
  val zipFile = new FileWriter("/Users/Shuxian/Documents/CSYE7200-Fall2016/predictemall/ZipCodes1.txt", true)

  // Due to Google API request limit, need to split the RDD by 2500 as a subset
  val part1 = usCoordinates take 2500 // Array[Coordinate]
  val key1 = "AIzaSyDXxUKKAooWrPYxk09yudhZCKVw5zTWYlw"
  val writer = new BufferedWriter(zipFile)

  val zips_1 = getZipCodes(part1, key1, pattern)
  for (zip <- zips_1) writer.write(zip + "\n")
  writer.close // flush the stream data into file

  //  val zips_1_RDD = sc.parallelize(zips_1)

  //  val zips_1 = for {
  //    coord <- part1
  //    url = s"https://maps.googleapis.com/maps/api/geocode/json?latlng=${coord.lat},${coord.lng}&key=${key1}"
  //    adds = (toJson(url) \ "results" \\ "formatted_address").toList.map(_.as[String])
  //    goodPart = for (pattern(_, state_zip, _) <- adds) yield state_zip
  //    zip = if (goodPart.isEmpty) "00000" else goodPart(0).split(" ")(1)
  //  } yield zip
  //
  //  println(zips_1.size)
  //  for (zip <- zips_1) println(zip)

  // Test on the first record and see what we can get through the reverse geocoding
  //  val coord = part1.take(1)
  //  val url_1 = s"https://maps.googleapis.com/maps/api/geocode/json?latlng=${coord(0).lat},${coord(0).lng}&key=AIzaSyCdGD2_7T1Bsi9dtp_dhgmL5p4gKTXrmUU"//  val req = url(url_1)
  //  val result = json(url_1)
  //  println(url_1)

  //  val textWrapper = Http(req OK as.String)
  //  val res = Await.result(textWrapper, 10.millisecond)
  //  val text = for (str <- res) str

  //  val text = textWrapper onSuccess {
  //    case content => content
  //  }

  //  val zip_1 = (result \ "results" \\ "formatted_address")(0).as[String].split(",")(2).split(" ")(2)
  //  val adds = (result \ "results" \\ "formatted_address").toList.map(_.as[String])
  //  // Regex matching
  //  val pattern = """(.*)\,\s([A-Z]*\s\d{5})\,\s(USA)""".r
  //  val goodPart = for (pattern(_, state_zip, _) <- adds) yield state_zip
  //  val zip = goodPart(0).split(" ")(1)
  //  println(zip)
}