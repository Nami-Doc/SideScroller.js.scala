package examplegame

import org.scalajs.dom
import org.scalajs.dom.HTMLElement


case class Tileset(tiles: Seq[Element])

// TODO maybe a GameMap shouldn't care about rendering
// and we'd do it in SideScroller#main? (or a separate fn for that purpose)
case class GameMap(ctx: dom.CanvasRenderingContext2D,
                   tilesets: Seq[Tileset]) {
  import GameMap._

  val height = tilesets.length
  val width = tilesets(0).tiles.length // nice hack m9

  val heightPx = height * tileSizePx
  val widthPx = width  * tileSizePx

  def draw(): Unit = {
    for {
      tileset <- tilesets
      tile <- tileset.tiles
    } Display(ctx, tile)
  }

  def mapTile(fn: (Element) => Element): Unit = {
    copy(tilesets = for {
      tileset <- tilesets
    } yield Tileset(for {
      tile <- tileset.tiles
    } yield fn(tile)))
  }
}

object GameMap {
  val tileSizePx = 3

  val sizeX = 20
  val sizeY = 20

  def charToElement(c: Char, pos: Point): Element = {
    c.toLower match {
      case ' ' => WalkableGround.Grass(pos)
      case 'm' => WalkableGround.Grass(pos) // TODO: castle ground
      case 'i' => WalkableGround.Grass(pos) // TODO: ice
      case 'w' => UnwalkableGround.Water(pos)
      case 'r' => UnwalkableGround.Rock(pos)
      case 'e' => UnwalkableGround.Rock(pos) // TODO: safeguard before water
      case 'f' => UnwalkableGround.Rock(pos) // TODO: lava
      case 'b' => UnwalkableGround.Rock(pos) // TODO: safeguard before lava
      case 't' => UnwalkableGround.Rock(pos) // TODO: ??
      case 'p' => Obstacle.Fir(pos) // TODO: text panel
      case 'g' => Obstacle.Fir(pos) // TODO: ??
      case 's' => Obstacle.Fir(pos) // TODO: ??
      case 'j' => Obstacle.Fir(pos) // TODO: ice rock
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
        charToElement(char, Point(x, y)) // y starts at 1
      }
    )
  }
}