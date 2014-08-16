package examplegame

import scala.scalajs.js.JSApp
import org.scalajs.dom
import org.scalajs.dom.HTMLElement
import org.scalajs.dom.extensions._
import scala.collection.mutable

object SideScroller extends JSApp {
  val tileSize = 40

  // @todo move that
  var keysPressed = mutable.Map[Int, Boolean](
    KeyCode.right -> false,
    KeyCode.left  -> false,

    KeyCode.up    -> false,
    KeyCode.down  -> false
  )

  def main(): Unit = {
    val main = dom.document.getElementById("main")

    registerKeyEvents()

    val mapText = dom.document.getElementById("map").innerHTML.trim
    val map = GameMap(GameMap.generate(main, mapText))

    // wiring
    main.style.width = s"${map.width * tileSize}px"
    main.style.height = s"${map.height * tileSize}px"
    println(map)

    // go go go !
    map.draw()
    // todo map.answerToKeymap ? or what
//    dom.setInterval(() => map.draw(), 300)
  }

  private def registerKeyEvents(): Unit = {
    def updateWith(setTo: Boolean): (dom.KeyboardEvent) => Unit = {
      (e: dom.KeyboardEvent) =>
        val which = e.keyCode
        if (keysPressed contains which) {
          println(s"Recognized key press: $which")
          keysPressed(which) = setTo
          e.preventDefault()
        }
    }

    // key pressed
    dom.document.onkeydown = updateWith(setTo = true)
    // key released
    dom.document.onkeyup = updateWith(setTo = false)
  }
}
