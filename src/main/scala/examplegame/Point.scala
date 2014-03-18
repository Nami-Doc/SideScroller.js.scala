package examplegame

import scala.collection.mutable

object Keys {
  val RIGHT = 39
  val LEFT  = 37

  val UP    = 38
  val DOWN  = 40
}

// vector...-ish way of doing it
case class Point(x: Int, y: Int)
object Point {
  def fromKeys(keys: mutable.Map[Int, Boolean]) = {
    val x = if (keys(Keys.RIGHT))
      1
    else if (keys(Keys.LEFT))
      -1
    else
      0

    val y = if (keys(Keys.UP))
      1
    else if (keys(Keys.DOWN))
      -1
    else
      0

    Point(x, y)
  }
}