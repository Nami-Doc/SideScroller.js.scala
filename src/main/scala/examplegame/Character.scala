package examplegame

import org.scalajs.dom.HTMLElement
import scala.collection.mutable

trait Element {
  val x: Int
  val y: Int
  val allowCollide: Boolean

  def tick: Element = this
}


case class Ground(override val x: Int, override val y: Int) extends Element {
  override val allowCollide = true
}

case class Obstacle(override val x: Int, override val y: Int)
  extends Element {

  override val allowCollide = false
}

case class Monster(override val x: Int, override val y: Int)
     extends Element {
  override val allowCollide = false

  //override def collide(c: Character): Unit = {
  //  // Aruna-ing.
  //}
}

case class Character(override val x: Int, override val y: Int)
     extends Element {
  override val allowCollide = false
  override def tick: Character = this //moveWith(Point.fromKeys(keymap))

  val speedX = 1
  val speedY = 1
}

object Ground {
  def grass(x: Int, y: Int) = new Ground(x, y)
}
object Obstacle {
  def fir(x: Int, y: Int) = new Obstacle(x, y)
}
object Monster { }