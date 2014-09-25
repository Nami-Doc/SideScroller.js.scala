package examplegame

import examplegame.Obstacle.Fir
import examplegame.UnwalkableGround.{Rock, Water}
import examplegame.WalkableGround.Grass

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
    case Character => implicitly[Behavior[Character]]
    case Monster => implicitly[Behavior[Monster]]

    case Grass => implicitly[Behavior[Grass]]
    case Water => implicitly[Behavior[Water]]
    case Rock => implicitly[Behavior[Rock]]
    case Fir => implicitly[Behavior[Fir]]
  }
}