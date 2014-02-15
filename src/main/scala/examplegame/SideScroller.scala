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
  def moveWith(p: Point) = { // TODO lenses n stuff
    copy(x = this.x + p.x, y = this.y + p.y)
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
    val frame = dom.document.getElementById("main")
    registerKeyEvents(frame)

    val character = Character(0, 0)
    val characterFrame = dom.document.createElement("div")
    characterFrame.id = "character"
    pulse(frame, characterFrame, character)
  }

  @tailrec
  def pulse(frame: HTMLElement, characterFrame: HTMLElement, c: Character): Character = {
    pulse(frame, characterFrame, c.moveWith(Point.fromKeys(keysPressed)))
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
    frame.onkeydown = updateWith(true)
    // key released
    frame.onkeyup = updateWith(false)
  }
}
