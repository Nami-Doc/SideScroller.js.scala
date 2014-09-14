package examplegame

import scala.scalajs.js.JSApp
import org.scalajs.dom
import org.scalajs.dom.HTMLElement
import org.scalajs.dom.extensions._
import scala.collection.mutable

object SideScroller extends JSApp {
  type Keymap = mutable.Map[Int, Boolean]

  // @todo move that
  var keysPressed: Keymap = mutable.Map[Int, Boolean](
    KeyCode.right -> false,
    KeyCode.left  -> false,

    KeyCode.up    -> false,
    KeyCode.down  -> false
  )

  def main(): Unit = {
    val main = dom.document.getElementById("main")
    val canvas = main.cast[dom.HTMLCanvasElement].getContext("2d").cast[dom.CanvasRenderingContext2D]

    registerKeyEvents()

    val mapText: String = dom.document.getElementById("map").innerHTML.trim
    val map = GameMap(canvas, GameMap.tilesFromText(mapText))

    // wiring
    main.style.width = s"1300px"
    main.style.height = s"400px"
    println(map)

    // go go go !
    map.draw()
    dom.setInterval({ () =>
      map.eachTile(el => Behavior.of(el)(map, el, keysPressed))
      map.draw()
    }, 300)
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
