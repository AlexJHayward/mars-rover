package domain

case class RobotPosition(coord: Coordinate, orientation: Orientation) {
  def pretty: String = s"(${coord.x}, ${coord.y}, ${orientation.pretty})"

  def applyMovement(direction: Direction): RobotPosition = direction match {
    case Direction.Forward => RobotPosition(coord.moveForward(orientation), orientation)
    case Direction.Left    => RobotPosition(coord, orientation.rotateLeft)
    case Direction.Right   => RobotPosition(coord, orientation.rotateRight)
  }
}
