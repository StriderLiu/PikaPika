package populationDensity

/**
  * Created by Shuxian on 12/6/16.
  */
import geo.Pokemons
import geo.Pokemons.Coordinate

object PopDensity extends App {

//    val lat = 53.602550
//    val lng = -113.444897
  //  val url=s"http://www.datasciencetoolkit.org/coordinates2statistics/${lat}%2c${lng}?statistics=population_density"
  //  val jsValue = Pokemons.toJson(url)(0)
  //  val popDensity= (jsValue \ "statistics" \ "population_density" \ "value").as[Int]
  //  println(popDensity)

  //  sources: http://www.datasciencetoolkit.org/developerdocs#coordinates2statistics
  def getPopDensity(coord: Coordinate): Int = {
    val url = s"http://www.datasciencetoolkit.org/coordinates2statistics/${coord.lat}%2c${coord.lng}?statistics=population_density"
    val jsValue = Pokemons.toJson(url)(0)
    val popDensity = (jsValue \ "statistics" \ "population_density" \ "value").as[Int]
    popDensity
  }

  //  <200 for rural, >=200 and <400 for midUrban, >=400 and <800 for subUrban, >800 for urban
  def getRural(coord: Coordinate): Int = if (getPopDensity(coord) < 200) 1 else 0

  def getMidUrban(coord: Coordinate): Int = getPopDensity(coord) match {
    case x if x >= 200 && x <400 => 1
    case _ => 0
  }

  def getSubUrban(coord: Coordinate): Int = getPopDensity(coord) match {
    case x if x >= 400 && x < 800 => 1
    case _ => 0
  }

  def getUrban(coord: Coordinate): Int = if (getPopDensity(coord) > 800) 1 else 0


}
