package examplegame

import scala.collection.mutable
import org.scalajs.dom.extensions.KeyCode

case class Point(x: Int, y: Int) {
  def +(that: Point) = {
    Point(x + that.x, y + that.y)
  }
  def -(that: Point) = {
    Point(x - that.x, y - that.y)
  }
}

object Point {

  // TODO should "Point" really care about DOM keycodes? dont think so
  def fromKeys(keys: mutable.Map[Int, Boolean]) = {
    val x = if (keys(KeyCode.right))
      1
    else if (keys(KeyCode.left))
      -1
    else
      0

    val y = if (keys(KeyCode.up))
      1
    else if (keys(KeyCode.down))
      -1
    else
      0

    Point(x, y)
  }
}