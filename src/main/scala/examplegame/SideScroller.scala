package examplegame

import scala.scalajs.js.JSApp
import org.scalajs.dom
import org.scalajs.dom.{HTMLElement, Event}
import org.scalajs.dom.extensions._
import scala.collection.mutable
import scala.collection.immutable

case class DisplayableElement(htmlEl: HTMLElement, el: Element) {
  def tick: this.type = {
    el.tick
    this
  }

  def draw: this.type = {
    htmlEl.style.left = s"${el.x * Map.sizeX}px"
    htmlEl.style.bottom = s"${el.y * Map.sizeY}px"
    this
  }
}

object DisplayableElement {
  def make(el: Element): DisplayableElement =
    DisplayableElement(null, el)
}

class Tileset(tiles: Seq[DisplayableElement]) {}
class Map(tilesets: Seq[Tileset]) {
  def draw: Unit = {

  }
}

/*
  def moveWith(p: Point) = {
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
 */

object Map {
  val sizeX = 20
  val sizeY = 20

  def charToElement(c: Char, x: Int, y: Int): Element = {
    c match {
      case ' ' => Ground.grass(x, y)
      case 's' => Obstacle.fir(x, y)
    }
  }

  def fromString(mapText: String) = new Map(
    for { (line, y) <- mapText.split('\n').zipWithIndex }
    yield new Tileset(
      for { (char, x) <- line.toList.zipWithIndex }
      yield DisplayableElement.make(Map.charToElement(char, x, y))
    )
  )
}

object SideScroller extends JSApp {
  val MAX_X = 30
  val MAX_Y = 30
  val map =
    """
      |
    """.stripMargin

  val frameWidth = 800
  val maxX = frameWidth - 40 // 40 is character width
  val frameHeight = 400
  val maxY = frameHeight - 40 // 40 is character height

  // @todo move that
  var keysPressed = mutable.Map[Int, Boolean](
    KeyCode.right -> false,
    KeyCode.left  -> false,

    KeyCode.up    -> false,
    KeyCode.down  -> false
  )

  def main(): Unit = {
    // wiring
    val frame = dom.document.getElementById("main")
    registerKeyEvents(frame)

    // char
    val cFrame = dom.document.createElement("div")
    cFrame.id = "character"
    frame.appendChild(cFrame)
    val character = DisplayableElement(cFrame, Character(0, 0))

    val map = Map.fromString(dom.document.getElementById("map").innerHTML)

    // go go go !
    map.draw
    // todo map.answerToKeymap ? or what
    dom.setInterval(() => map.draw, 100)
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
