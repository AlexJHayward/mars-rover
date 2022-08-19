package domain

import domain.MarsRoverError.DirectionParsingError

sealed trait Direction

object Direction {
  case object Forward extends Direction
  case object Left    extends Direction
  case object Right   extends Direction

  def fromString(str: String): Either[MarsRoverError, Direction] = str.toUpperCase match {
    case "F" => util.Right(Forward)
    case "L" => util.Right(Left)
    case "R" => util.Right(Right)
    case _   => util.Left(DirectionParsingError(str))
  }
}
