package domain

import domain.MarsRoverError.CoordinateParsingError

import scala.util.Try

case class Coordinate(x: Int, y: Int)

object Coordinate {
  // 0,2
  def fromString(str: String): Either[MarsRoverError, Coordinate] =
    str.split(",").toList match {
      case xStr :: yStr :: Nil =>
        (for {
          x <- Try(xStr.toInt)
          y <- Try(yStr.toInt)
        } yield Coordinate(x, y)).toOption.toRight(CoordinateParsingError(str))
      case _ => Left(CoordinateParsingError(str))
    }

  implicit class CoordinateSyntax(c: Coordinate) {
    // format: off
    def moveForward(orientation: Orientation): Coordinate = orientation match {
      case Orientation.North => Coordinate(c.x,     c.y + 1)
      case Orientation.East  => Coordinate(c.x + 1, c.y)
      case Orientation.South => Coordinate(c.x,     c.y - 1)
      case Orientation.West  => Coordinate(c.x - 1, c.y)
    }
    // format: on
  }
}
