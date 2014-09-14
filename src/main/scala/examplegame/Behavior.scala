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
  implicit object GrassBehavior extends Behavior[Grass]
  implicit object WaterBehavior extends Behavior[Water]
  implicit object RockBehavior extends Behavior[Rock]
  implicit object FirBehavior extends Behavior[Fir]

  def of[T <: Element](el: T) = el match {
    // explicitly list every cases so that we can
    //  add other implicit objects later down and not change this code
    case Character => implicitly[Behavior[Character]]
    case Monster => implicitly[Behavior[Monster]]

    case Grass => implicitly[Behavior[Grass]]
    case Water => implicitly[Behavior[Water]]
    case Rock => implicitly[Behavior[Rock]]
    case Fir => implicitly[Behavior[Fir]]
  }
}