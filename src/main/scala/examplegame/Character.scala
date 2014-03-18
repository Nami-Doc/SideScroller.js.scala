package examplegame

import org.scalajs.dom.HTMLElement
import scala.util.Random
import scala.collection.mutable

trait Element {
  val el: HTMLElement
  val x: Int
  val y: Int

  def tick: Element

  def draw: this.type = {
    el.style.left = s"${x}px"
    el.style.bottom = s"${y}px"
    this
  }
}

case class Obstacle(override val el: HTMLElement, override val x: Int, override val y: Int)
  extends Element {

  def tick: Obstacle = this
}

case class Monster(override val el: HTMLElement, override val x: Int, override val y: Int,
                   baseX: Int, baseY: Int)
     extends Element {
  val wanderX: Int = 2
  val wanderY: Int = 2

  // Monsters should "wander off" aimlessly, base$ + wander$
  def tick: Monster = {
    // mobs will randomly move
    val r = new Random()
    // this is so dumb I can't get over it ...
    copy(x = baseX + r.nextInt(wanderX * 2) - wanderX,
         y = baseY + r.nextInt(wanderY * 2) - wanderY)
  }
}

case class Character(override val el: HTMLElement, override val x: Int, override val y: Int,
                     keymap: mutable.Map[Int, Boolean])
     extends Element {
  val speedX = 10
  val speedY = 20

  def tick: Character = moveWith(Point.fromKeys(keymap))

  def moveWith(p: Point) = { // TODO lenses n stuff
    var newX = x + (p.x * speedX)
    if (newX < 0)
      newX = 0
    if (newX > SideScroller.maxX) // (width=400)-(char width)
      newX = SideScroller.maxX

    var newY = y + (p.y * speedY)
    if (newY < 0)
      newY = 0
    if (newY > SideScroller.maxY)
      newY = SideScroller.maxY

    copy(x = newX, y = newY)
  }
}