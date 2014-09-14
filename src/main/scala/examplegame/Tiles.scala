package examplegame

sealed trait Element {
  val pos: Point
  val allowCollide: Boolean
  val color: String

  def tick: Element = this
}


case class WalkableGround(override val pos: Point)
     extends Element {
  override val allowCollide = true
  override val color = "green"
}

case class UnwalkableGround(override val pos: Point)
     extends Element {
  override val allowCollide = false
  override val color = "black"
}

case class Obstacle(override val pos: Point)
  extends Element {

  override val allowCollide = false
  override val color = "grey"
}

case class Monster(override val pos: Point)
     extends Element {
  override val allowCollide = false
  override val color = "red"

  //override def collide(c: Character): Unit = {
  //  // Aruna-ing.
  //}
}

case class Character(override val pos: Point)
     extends Element {
  override val allowCollide = false
  override val color = "yellow"

  override def tick: Character = this //moveWith(Point.fromKeys(keymap))

  val speedX = 1
  val speedY = 1
}

object WalkableGround {
  def grass(pos: Point) = new WalkableGround(pos)
}
object UnwalkableGround {
  def water(pos: Point) = new UnwalkableGround(pos)
  def rock(pos: Point) = new UnwalkableGround(pos)
}
object Obstacle {
  def fir(pos: Point) = new Obstacle(pos)
}
object Monster { }