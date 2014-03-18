package examplegame

import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.dom.{HTMLElement, Event, extensions}
import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.immutable

class Scene(var els: List[Element]) {
  // :'(
  def draw: Unit = {
    els = for (
      (el, i) <- els.zipWithIndex
    ) yield el.tick.draw
  }
}

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

    // char
    val cFrame = dom.document.createElement("div")
    cFrame.id = "character"
    frame.appendChild(cFrame)
    val character = Character(cFrame, 0, 0, keysPressed)

    // obstacle
    val oFrame = dom.document.createElement("div")
    oFrame.classList.add("obstacle")
    frame.appendChild(oFrame)
    val obstacle = Obstacle(oFrame, 50, 50)

    // scene
    val scene = new Scene(List(character, obstacle))
    scene.draw
    // go go go !
    dom.setInterval(() => scene.draw, 100)
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
