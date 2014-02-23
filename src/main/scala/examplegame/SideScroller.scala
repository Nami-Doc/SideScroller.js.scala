package examplegame

import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.dom.{HTMLElement, Event, extensions}
import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.immutable

object Keys {
  val RIGHT = 39
  val LEFT  = 37

  val UP    = 38
  val DOWN  = 40
}

object CollisionManager

class Map {
  val map =
    """
      |        *
      |________ ______
    """.stripMargin

  def draw(frame: HTMLElement): Unit = {
    
  }
}

case class Character(x: Int, y: Int) {
  val speedX = 10
  val speedY = 20

  def moveWith(p: Point) = { // TODO lenses n stuff
    var newX = x + (p.x * speedX)
    if (newX < 0)
      newX = 0
    if (newX > SideScroller.maxX) // (width=400)-(char width)
      newX = SideScroller.maxX

    var newY = y + (p.y * speedY)
    if (newY < 0)
      newY = 0
    if (newY > SideScroller.maxY)
      newY = SideScroller.maxY

    copy(x = newX, y = newY)
  }
}

// vectors...-ish way of doing it
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

object SideScroller {
  val frameWidth = 800
  val maxX = frameWidth - 40 // 40 is character width
  val frameHeight = 400
  val maxY = frameHeight - 40 // 40 is character height

  var keysPressed = mutable.Map[Int, Boolean](
    Keys.RIGHT -> false,
    Keys.LEFT  -> false,

    Keys.UP    -> false,
    Keys.DOWN  -> false
  )

  def main(): Unit = {
    // wiring
    val frame = dom.document.getElementById("main")
    registerKeyEvents(frame)
    val cFrame = dom.document.createElement("div")
    cFrame.id = "character"
    frame.appendChild(cFrame)

    // gameplay !
    var character = Character(0, 0)
    doDraw(frame, cFrame, character)
    dom.setInterval(() => character = doDraw(frame, cFrame, character.moveWith(Point.fromKeys(keysPressed))), 100)
  }

  def doDraw(frame: HTMLElement, cFrame: HTMLElement, character: Character): Character = {
    cFrame.style.left = s"${character.x}px"
    cFrame.style.bottom = s"${character.y}px"
    character
  }

  private def registerKeyEvents(frame: HTMLElement): Unit = {
    def updateWith(v: => Boolean): Function1[dom.KeyboardEvent, Unit] = {
      {(e: dom.KeyboardEvent) =>
        val which = e.keyCode.toInt
        if (keysPressed contains which) {
          keysPressed(which) = v
        }
      }
    }

    // key pressed
    dom.document.onkeydown = updateWith(true)
    // key released
    dom.document.onkeyup = updateWith(false)
  }
}
