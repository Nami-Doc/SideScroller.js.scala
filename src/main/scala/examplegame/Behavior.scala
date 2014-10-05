package examplegame

import examplegame.UnwalkableGround.{Rock, Water}
import examplegame.WalkableGround.Grass
import examplegame.Obstacle.Fir

trait Behavior[T <: Element] {
  def apply(ctx: Behavior.Ctx): Unit = Unit
}

object Behavior {
  type Ctx = (GameMap, Element, SideScroller.Keymap)

  implicit object MonsterBehavior extends Behavior[Monster] {
    override def apply(ctx: Ctx): Unit = {

    }
  }
  implicit object CharacterBehavior extends Behavior[Character] {
    override def apply(ctx: Ctx): Unit = {

    }
  }
  // TODO: this code uses implicits ONLY so that later parts can define
  //  new behaviors for grass/water/.. without altering this file,
  //  but is it possible with these defaults? Maybe if you import later
  //  (this is why trait Behavior is not sealed
  //   should trait Element not be sealed either? it prolly should)
  implicit object GrassBehavior extends Behavior[Grass]
  implicit object WaterBehavior extends Behavior[Water]
  implicit object RockBehavior extends Behavior[Rock]
  implicit object FirBehavior extends Behavior[Fir]

  def of[T <: Element](el: T) = el match {
    case Character(_) => implicitly[Behavior[Character]]
    //case Monster(_) => implicitly[Behavior[Monster]]

    case Grass(_) => implicitly[Behavior[Grass]]
    case Water(_) => implicitly[Behavior[Water]]
    case Rock(_) => implicitly[Behavior[Rock]]
    case Fir(_) => implicitly[Behavior[Fir]]
  }
}