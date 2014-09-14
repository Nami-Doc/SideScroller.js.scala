package examplegame

import scala.collection.mutable

trait Behavior[T <: Element] {
  def apply(map: GameMap, el: Element, keys: mutable.Map): Unit
}

object Behavior {
  def apply[T <: Element](map: GameMap, el: T, keys: mutable.Map)
              (implicit B: Behavior[T]) =
    B(map, el, keys)

  implicit object MonsterBehavior extends Behavior[Monster] {
    def apply(map: GameMap, el: Element, keys: mutable.Map): Unit = {

    }
  }
  implicit object CharacterBehavior extends Behavior[Character] {
    def apply(map: GameMap, el: Element, keys: mutable.Map): Unit = {

    }
  }
  implicit object NoBehavior extends Behavior[_] {
    def apply(map: GameMap, el: Element, keys: mutable.Map): Unit = {

    }
  }
}