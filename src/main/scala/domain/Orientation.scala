package domain

import domain.MarsRoverError.OrientationParsingError

sealed trait Orientation {
  def pretty: String = this.getClass.getSimpleName.head.toString
}

object Orientation {
  case object North extends Orientation
  case object East  extends Orientation
  case object South extends Orientation
  case object West  extends Orientation

  implicit class OrientationSyntax(orientation: Orientation) {
    def rotateLeft: Orientation = orientation match {
      case North => West
      case East  => North
      case South => East
      case West  => South
    }

    def rotateRight: Orientation = orientation match {
      case North => East
      case East  => South
      case South => West
      case West  => North
    }
  }

  def fromString(str: String): Either[MarsRoverError, Orientation] = str.toUpperCase match {
    case "N" => Right(North)
    case "E" => Right(East)
    case "S" => Right(South)
    case "W" => Right(West)
    case _   => Left(OrientationParsingError(str))
  }
}
