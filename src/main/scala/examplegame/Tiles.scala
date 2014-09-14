package examplegame

import org.scalajs.dom

sealed trait Element {
  val pos: Point
  val allowCollide: Boolean

  def tick: Element = this
}


abstract class WalkableGround(override val pos: Point)
     extends Element {
  override val allowCollide = true
}

abstract class UnwalkableGround(override val pos: Point)
     extends Element {
  override val allowCollide = false
}

abstract class Obstacle(override val pos: Point)
  extends Element {

  override val allowCollide = false
}

abstract class Monster(override val pos: Point)
     extends Element {
  override val allowCollide = false

  //override def collide(c: Character): Unit = {
  //  // Aruna-ing.
  //}
}

case class Character(override val pos: Point)
     extends Element {
  override val allowCollide = false

  override def tick: Character = this //moveWith(Point.fromKeys(keymap))

  val speedX = 1
  val speedY = 1
}

object WalkableGround {
  case class Grass(override val pos: Point) extends WalkableGround(pos)
}
object UnwalkableGround {
  case class Water(override val pos: Point) extends UnwalkableGround(pos)
  case class Rock(override val pos: Point) extends UnwalkableGround(pos)
}
object Obstacle {
  case class Fir(override val pos: Point) extends Obstacle(pos)
}
object Monster { }

// ---

// display manages anything display-related
// TODO also manage camera move (need to have character)
object Display {
  private def color(el: Element): String = el match {
    case WalkableGround.Grass(pos)   => "green"

    case UnwalkableGround.Water(pos) => "blue"
    case UnwalkableGround.Rock(pos)  => "grey"

    case Obstacle.Fir(pos)           => "darkgreen"

    case Character(pos)              => "yellow"

    case _                           => "black"
  }

  def apply(ctx: dom.CanvasRenderingContext2D, el: Element): Unit = {
    ctx.fillStyle = color(el)

    val s = GameMap.tileSizePx // size
    ctx.fillRect(el.pos.x * s, el.pos.y * s, s, s)
  }
}