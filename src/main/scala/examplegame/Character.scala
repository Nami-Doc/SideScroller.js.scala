package examplegame

import org.scalajs.dom.HTMLElement
import scala.collection.mutable

trait Element {
  val pos: Point
  val allowCollide: Boolean

  def tick: Element = this
}


case class WalkableGround(override val pos: Point)
     extends Element {
  override val allowCollide = true
}

case class UnwalkableGround(override val pos: Point)
     extends Element {
  override val allowCollide = false
}

case class Obstacle(override val pos: Point)
  extends Element {

  override val allowCollide = false
}

case class Monster(override val pos: Point)
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
  def grass(pos: Point) = new WalkableGround(pos)
}
object UnwalkableGround {
  def water(pos: Point) = new UnwalkableGround(pos)
}
object Obstacle {
  def fir(pos: Point) = new Obstacle(pos)
  def rock(pos: Point) = new Obstacle(pos)
}
object Monster { }