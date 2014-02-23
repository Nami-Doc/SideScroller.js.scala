package examplegame

case class Character(x: Int, y: Int) {
  val speedX = 10
  val speedY = 20

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