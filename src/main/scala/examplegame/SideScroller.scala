package examplegame

import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.dom.{HTMLElement, Event, extensions}
import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.immutable

object SideScroller {
  val frameWidth = 800
  val maxX = frameWidth - 40 // 40 is character width
  val frameHeight = 400
  val maxY = frameHeight - 40 // 40 is character height

  // @todo move that
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
