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
}

case class Character(x: Int, y: Int) {
  val speedX = 5
  val speedY = 20

  def moveWith(p: Point) = { // TODO lenses n stuff
    copy(x = x + (p.x * speedX), y = y + (p.y * speedY))
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

    val y = 0

    Point(x, y)
  }
}

object SideScroller {
  var keysPressed = mutable.Map[Int, Boolean](
    Keys.RIGHT -> false,
    Keys.LEFT  -> false
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
    dom.console.log("Do tick")
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
