package examplegame

import scala.scalajs.js.JSApp
import org.scalajs.dom
import org.scalajs.dom.HTMLElement
import org.scalajs.dom.extensions._
import scala.collection.mutable

case class DisplayableElement(htmlEl: HTMLElement, el: Element) {
  def tick: this.type = {
    el.tick
    this
  }

  def draw: this.type = {
    htmlEl.style.left = s"${el.pos.x * Map.sizeX}px"
    htmlEl.style.bottom = s"${el.pos.y * Map.sizeY}px"
    this
  }
}

object DisplayableElement {
  def make(el: Element): DisplayableElement = {
    val htmlEl = dom.document.createElement("div")
    htmlEl.classList add el.getClass.getName // meeeh :(
    DisplayableElement(htmlEl, el)
  }
}

case class Tileset(tiles: Seq[DisplayableElement])

class Map(tilesets: Seq[Tileset]) {
  def draw(): Unit = {
    for {
      tileset <- tilesets
      tile <- tileset.tiles
    } tile.draw
  }
}

object Map {
  val sizeX = 20
  val sizeY = 20

  def charToElement(c: Char, pos: Point): Element = {
    c.toLower match {
      case ' ' => WalkableGround.grass(pos)
      case 'm' => WalkableGround.grass(pos) // TODO: castle ground
      case 'i' => WalkableGround.grass(pos) // TODO: ice
      case 'w' => UnwalkableGround.water(pos)
      case 'r' => UnwalkableGround.rock(pos)
      case 'e' => UnwalkableGround.rock(pos) // TODO: safeguard before water
      case 'f' => UnwalkableGround.rock(pos) // TODO: lava
      case 'b' => UnwalkableGround.rock(pos) // TODO: safeguard before lava
      case 't' => UnwalkableGround.rock(pos) // TODO: ??
      case 'p' => Obstacle.fir(pos) // TODO: text panel
      case 'g' => Obstacle.fir(pos) // TODO: ??
      case 's' => Obstacle.fir(pos) // TODO: ??
      case 'j' => Obstacle.fir(pos) // TODO: ice rock
      case '*' => Character(pos)
    }
  }

  def tilesFromString(mapText: String) =
    for {(line, y) <- mapText.split("\n").zipWithIndex}
    yield Tileset(
      for {(char, x) <- line.toList.zipWithIndex}
      yield DisplayableElement.make(charToElement(char, Point(x, y)))
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
    registerKeyEvents()

    val mapText = dom.document.getElementById("map").innerHTML
    println(mapText)
    val map = new Map(Map.tilesFromString(mapText))

    // go go go !
    map.draw()
    // todo map.answerToKeymap ? or what
    dom.setInterval(() => map.draw(), 100)
  }

  private def registerKeyEvents(): Unit = {
    def updateWith(setTo: Boolean): (dom.KeyboardEvent) => Unit = {
      (e: dom.KeyboardEvent) =>
        val which = e.keyCode
        if (keysPressed contains which) {
          keysPressed(which) = setTo
        }
    }

    // key pressed
    dom.document.onkeydown = updateWith(setTo = true)
    // key released
    dom.document.onkeyup = updateWith(setTo = false)
  }
}
