package examplegame

import org.scalajs.dom

case class GameMap(ground: Seq[Seq[Tile]], details: Seq[Seq[Tile]],
                   entities: Seq[Entity]) {
}

case class Renderer() {
  val tileSizePx = 3

  val tiles = Map(
    "v" -> Tile("path/to/thing", Walkable())
  )

  def render(map: GameMap): Unit = {
    for {
      xs <- map.ground
      x <- xs
    } yield 1
  }

  def apply(ctx: dom.CanvasRenderingContext2D): Unit = {
//    ctx.fillStyle = color(el)
//
//    val s = GameMap.tileSizePx
//    ctx.fillRect(x * s, y * s, s, s)
  }
}

object GameMap {
  val sizeX = 20
  val sizeY = 20
}