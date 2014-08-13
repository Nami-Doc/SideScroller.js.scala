package examplegame

import org.scalajs.dom
import org.scalajs.dom.HTMLElement

case class DisplayableElement(htmlEl: HTMLElement, el: Element) {
  def tick: this.type = {
    el.tick
    this
  }

  def draw: this.type = {
    htmlEl.style.left = s"${el.pos.x * GameMap.sizeX}px"
    htmlEl.style.bottom = s"${el.pos.y * GameMap.sizeY}px"
    this
  }
}

object DisplayableElement {
  def make(parent: HTMLElement, el: Element): DisplayableElement = {
    val htmlEl = dom.document.createElement("div")
    htmlEl.classList add "game-element"
    htmlEl.classList add el.kind
    parent appendChild htmlEl
    DisplayableElement(htmlEl, el)
  }
}

case class Tileset(tiles: Seq[DisplayableElement])

class GameMap(val tilesets: Seq[Tileset]) {
  def draw(): Unit = {
    for {
      tileset <- tilesets
      tile <- tileset.tiles
    } tile.draw
  }
}

object GameMap {
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

  def generate(parent: HTMLElement, mapText: String) = {
    // can't split by '\n' char because of a ScalaJS bug
    // (on the version I have locally, should be fixed by now)
    val lines = mapText split "\n"
    val numLines = lines.length
    for {(line, y) <- lines.zipWithIndex}
    yield Tileset(
      for {(char, x) <- line.toList.zipWithIndex}
      yield {
        val relY = numLines - (y - 1) // bottom is 0
        println(s"Creating elem at $x/$relY")
        DisplayableElement.make(
          parent,
          charToElement(char, Point(x, relY)) // y starts at 1
        )
      }
    )
  }
}