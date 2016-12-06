package weather

/**
  * Created by Shuxian on 12/5/16.
  */
import geo.Pokemons
import java.util.Calendar
import java.util.Date

import play.api.libs.json.JsValue


object WeatherCrawler extends App{

  case class Coordinate(lat: Double, lng: Double)

  val timeStamp = Calendar.getInstance.getTime
//  println(timeStamp)
//  val lat=53.602550
//  val long= -113.444897
  val key="230d97a0808f8c0bb2c722ea6e9ba251"
//  val url=s" https://api.darksky.net/forecast/${key}/${lat},${long}"

  def getWeatherJson(coord: Coordinate, key: String) ={
//    coord <- data
    val url=s" https://api.darksky.net/forecast/${key}/${coord.lat},${coord.lng}"
    val jsValue=Pokemons.toJson(url)
    jsValue
  }

  def getContinent(jsValue: JsValue):String=(jsValue \ "timezone").as[String].split("/")(0)

  def getIcon(jsValue: JsValue):String=(jsValue \ "currently" \ "summary").as[String].replace(" ","")

  def getPressure(jsValue: JsValue):Double=(jsValue \ "currently" \ "pressure").as[Double]

  def getTemperature(jsValue: JsValue):Double=(jsValue \ "currently" \ "temperature").as[Double]

  def getWindSpeed(jsValue: JsValue):Double=(jsValue \ "currently" \ "windSpeed").as[Double]

  def getWindBearing(jsValue: JsValue):Int=(jsValue \ "currently" \ "windBearing").as[Int]

  def getSunriseTime(jsValue: JsValue):Date=new Date(((jsValue \ "daily" \ "data" )(0) \ "sunriseTime").as[Long] * 1000)

  def getSunsetTime(jsValue: JsValue):Date = new Date(((jsValue \ "daily" \ "data" )(0) \ "sunsetTime").as[Long] * 1000)

  def getSunriseHour(date: Date)=date.getHours

  def getSunriseMinute(date: Date)=date.getMinutes

  def getSunsetHour(date:Date) =date.getHours

  def getSunsetMinute(date:Date) = date.getMinutes

  def getSunriseMinutesMidnight(sunriseHour:Int, sunriseMinute:Int)=sunriseHour *60 +sunriseMinute

  def getSunsetMinutesMidnight(sunsetHour: Int, sunsetMinute:Int)=sunsetHour * 60 + sunsetMinute

//  val jsValue=Pokemons.toJson(url)
//  val continent = (jsValue \ "timezone").get.as[String].split("/")(0)
//  val icon=(jsValue \ "currently" \ "summary").get.as[String].replace(" ","")
//  val pressure = (jsValue \ "currently" \ "pressure").get.as[Double]
//  val temperature = (jsValue \ "currently" \ "temperature").get.as[Double]
//  val windSpeed = (jsValue \ "currently" \ "windSpeed").get.as[Double]
//  val windBearing=(jsValue \ "currently" \ "windBearing").get.as[Int]
//  val sunriseTime = new Date(((jsValue \ "daily" \ "data" )(0) \ "sunriseTime").get.as[Long] * 1000)
//  val sunsetTime = new Date(((jsValue \ "daily" \ "data" )(0) \ "sunsetTime").get.as[Long] * 1000)

//  val sunriseHour = sunriseTime.getHours
//  val sunriseMinute = sunriseTime.getMinutes
//  val sunsetHour = sunsetTime.getHours
//  val sunsetMinute = sunsetTime.getMinutes
//  val sunriseMinutesMidnight = sunriseHour * 60 + sunriseMinute
//  val sunsetMinutesMidnight = sunsetHour * 60 + sunsetMinute


  //  'sunriseMinutesMidnight',

//  'sunriseMinutesSince',
//  'sunsetMinutesMidnight',

//  'sunsetMinutesBefore',
//  val time= new Date(sunriseTime * 1000)

//  println(sunriseHour)
}
