package geo

/**
  * Created by Shuxian on 12/6/16.
  */

import geo.Pokemons.Coordinate
import play.api.libs.json.{JsValue, Json}

import scala.io.Source.fromURL

object GeoCoding extends App{

  def toJson(url: String): JsValue = Json.parse(fromURL(url).mkString)

  val key = "AIzaSyDXxUKKAooWrPYxk09yudhZCKVw5zTWYlw"

//  val url=s"https://maps.googleapis.com/maps/api/geocode/json?address=Queensberry+St,+Boston,+MA+02215&key=${key}"

  def getCoordianate(address: String,key:String)={
    val url=s"https://maps.googleapis.com/maps/api/geocode/json?address=${address}&key=${key}"
    val coord=((toJson(url)\"results" )(0)\"geometry" \ "location")
    Coordinate((coord \ "lat").as[Double],(coord \ "lng").as[Double])
  }


//  val jsValue=toJson(url)
////  println(jsValue)
//  val coord=((jsValue\"results" )(0)\"geometry" \ "location").get
//  val lat=(coord \ "lat").as[Double]
//
//  val lng = (coord \ "lng").as[Double]
////  val pid=(jsValue\"results" \"place_id").get.as[String]
//  println(lat)

}
