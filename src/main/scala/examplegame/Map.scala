package examplegame

import org.scalajs.dom.HTMLElement

object CollisionManager

sealed trait Object {
  val width = 40
}

class Floor extends Object
class Block extends Object

class Map {
  def getObject(o: Char): Object = o match {
    case '* => new Block
    case '_ => new Floor
  }

  val map =
    """
      |        *
      |________ ______
    """.stripMargin.replace('|', ' ')

  def draw(frame: HTMLElement): Unit = {

  }
}