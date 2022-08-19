package domain

import domain.MarsRoverError.GridParsingFailure

import scala.util.Try

case class Grid(width: Int, height: Int) {
  def containsCoordinate(coord: Coordinate): Boolean = {
    val withinWidth  = coord.x >= 0 && coord.x <= width
    val withinHeight = coord.y >= 0 && coord.y <= height

    withinWidth && withinHeight
  }
}

object Grid {
  def fromString(s: String): Either[MarsRoverError, Grid] = s.split(" ").toList match {
    case widthString :: heightString :: Nil =>
      (for {
        width  <- parseGridDimension(widthString)
        height <- parseGridDimension(heightString)
      } yield Grid(width, height)).toRight(GridParsingFailure(s))
    case _ => Left(GridParsingFailure(s))
  }

  private def parseGridDimension(str: String): Option[Int] = for {
    integer         <- Try(str.toInt).toOption
    positiveInteger <- Option(integer).filter(_ > 0)
  } yield positiveInteger
}
