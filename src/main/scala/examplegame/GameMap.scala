package examplegame

import org.scalajs.dom


case class Tileset(tiles: Seq[Element])

// TODO maybe a GameMap shouldn't care about rendering
// and we'd do it in SideScroller#main? (or a separate fn for that purpose)
case class GameMap(ctx: dom.CanvasRenderingContext2D,
                   tilesets: Seq[Tileset]) {
  import GameMap._

  val height = tilesets.length
  val width = tilesets.head.tiles.length // nice hack m9

  val heightPx = height * tileSizePx
  val widthPx = width  * tileSizePx

  def draw(): Unit = {
    for {
      (tileset, y) <- tilesets.zipWithIndex
      (tile, x) <- tileset.tiles.zipWithIndex
    } Display(ctx, tile, y, x)
  }

  def mapTiles(fn: (Element) => Element): GameMap = {
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

  def charToElement(c: Char): Element = {
    c.toLower match {
      case ' ' => WalkableGround.Grass()
      case 'm' => WalkableGround.Grass() // TODO: castle ground
      case 'i' => WalkableGround.Grass() // TODO: ice
      case 'w' => UnwalkableGround.Water()
      case 'r' => UnwalkableGround.Rock()
      case 'e' => UnwalkableGround.Rock() // TODO: safeguard before water
      case 'f' => UnwalkableGround.Rock() // TODO: lava
      case 'b' => UnwalkableGround.Rock() // TODO: safeguard before lava
      case 't' => UnwalkableGround.Rock() // TODO: ??
      case 'p' => Obstacle.Fir() // TODO: text panel
      case 'g' => Obstacle.Fir() // TODO: ??
      case 's' => Obstacle.Fir() // TODO: ??
      case 'j' => Obstacle.Fir() // TODO: ice rock
      case '*' => Character()
    }
  }

  def tilesFromText(mapText: String) = {
    // can't split by '\n' char because of a ScalaJS bug
    // (on the version I have locally, should be fixed by now)
    val lines = mapText split "\n"
    for {line <- lines}
    yield Tileset(
      for {char <- line.toList}
      yield {
        charToElement(char) // y starts at 1
      }
    )
  }
}