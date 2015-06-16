package examplegame

import org.scalajs.dom

sealed trait Element {
  val allowCollide: Boolean
}


abstract class WalkableGround
     extends Element {
  override val allowCollide = true
}

abstract class UnwalkableGround
     extends Element {
  override val allowCollide = false
}

abstract class Obstacle
  extends Element {

  override val allowCollide = false
}

abstract class Monster
     extends Element {
  override val allowCollide = false

  //override def collide(c: Character): Unit = {
  //  // Aruna-ing.
  //}
}

case class Character()
     extends Element {
  override val allowCollide = false

  val speedX = 1
  val speedY = 1
}

object WalkableGround {
  case class Grass() extends WalkableGround
}
object UnwalkableGround {
  case class Water() extends UnwalkableGround
  case class Rock() extends UnwalkableGround
}
object Obstacle {
  case class Fir() extends Obstacle
}
object Monster { }

// ---

// display manages anything display-related
// TODO also manage camera move (need to have character)
object Display {
  private def color(el: Element): String = el match {
    case WalkableGround.Grass()   => "green"

    case UnwalkableGround.Water() => "blue"
    case UnwalkableGround.Rock()  => "grey"

    case Obstacle.Fir()           => "darkgreen"

    case Character()              => "yellow"

    case _                           => "black"
  }

  def apply(ctx: dom.CanvasRenderingContext2D, el: Element, y: Int, x: Int): Unit = {
    ctx.fillStyle = color(el)

    val s = GameMap.tileSizePx
    ctx.fillRect(x * s, y * s,
                 s, s)
  }
}