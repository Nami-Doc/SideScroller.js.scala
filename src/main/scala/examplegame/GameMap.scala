package examplegame

import org.scalajs.dom
import org.scalajs.dom.HTMLElement


case class Tileset(tiles: Seq[Element])

// TODO maybe a GameMap shouldn't care about rendering
// and we'd do it in SideScroller#main? (or a separate fn for that purpose)
case class GameMap(ctx: dom.CanvasRenderingContext2D,
                   tilesets: Seq[Tileset]) {
  val tileSizePx = 10 // in pixels

  val height = tilesets.length
  val width = tilesets(0).tiles.length // nice hack m9

  val heightPx = height * tileSizePx
  val widthPx = width  * tileSizePx

  def draw(): Unit = {
    // clear...
    ctx.fillStyle = "black"
    ctx.fillRect(0, 0, widthPx, heightPx)

    for {
      tileset <- tilesets
      tile <- tileset.tiles
    } {
      ctx.fillStyle = tile.color
      println(tile.pos.x * tileSizePx, tile.pos.y * tileSizePx,
        tileSizePx, tileSizePx)
      ctx.fillRect(tile.pos.x * tileSizePx, tile.pos.y * tileSizePx,
        tileSizePx, tileSizePx)
    }
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

  def tilesFromText(mapText: String) = {
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
        charToElement(char, Point(x, relY)) // y starts at 1
      }
    )
  }
}