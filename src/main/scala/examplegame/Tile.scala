package examplegame



sealed trait Walkability
case class Walkable() extends Walkability
case class Unwalkable() extends Walkability

case class Tile(url: String, walkable: Walkability) {
  def isWalkabke = walkable match {
    case Walkable() => true
    case Unwalkable() => false
  }
}

sealed trait Entity
case class Character() extends Entity