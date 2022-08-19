package domain

import domain.MarsRoverError.{DirectionParsingError, RoverInstructionParsingError}

case class RoverInstructions(initialPosition: RoverPosition, directions: List[Direction])

object RoverInstructions {
  def fromString(str: String): Either[MarsRoverError, RoverInstructions] = {
    val sanitised          = str.filterNot(_.isWhitespace).toUpperCase
    val instructionPattern = """\(([0-9],[0-9]),([NSEW])\)([FLR]*)""".r

    sanitised match {
      case instructionPattern(maybePosition, maybeOrientation, maybeDirections) =>
        for {
          initialPosition <- Coordinate.fromString(maybePosition)
          orientation     <- Orientation.fromString(maybeOrientation)
          position = RoverPosition(initialPosition, orientation)
          instructions <- parseDirections(maybeDirections)
        } yield RoverInstructions(position, instructions)
      case _ => Left(RoverInstructionParsingError(str))
    }
  }

  private def parseDirections(str: String): Either[MarsRoverError, List[Direction]] = {
    //todo would be better to not be discarding the errors from the DirectionParser here, but the Try is easier to work with later.
    val (rights, lefts) =
      str.toList.map(c => Direction.fromString(c.toString).toTry).partition(_.isSuccess)

    if (lefts.nonEmpty) Left(DirectionParsingError(str))
    else Right(rights.map(_.get)) // the get here is safe thanks to the earlier partition.
  }
}
