package domain

case class RoverPosition(coord: Coordinate, orientation: Orientation) {
  def pretty: String = s"(${coord.x}, ${coord.y}, ${orientation.pretty})"

  def applyMovement(direction: Direction): RoverPosition = direction match {
    case Direction.Forward => RoverPosition(coord.moveForward(orientation), orientation)
    case Direction.Left    => RoverPosition(coord, orientation.rotateLeft)
    case Direction.Right   => RoverPosition(coord, orientation.rotateRight)
  }
}
