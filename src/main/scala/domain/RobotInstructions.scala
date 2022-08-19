package domain

import domain.MarsRoverError.{DirectionParsingError, RobotInstructionParsingError}

case class RobotInstructions(initialPosition: RobotPosition, directions: List[Direction])

object RobotInstructions {
  def fromString(str: String): Either[MarsRoverError, RobotInstructions] = {
    val sanitised          = str.filterNot(_.isWhitespace).toUpperCase
    val instructionPattern = """\(([0-9],[0-9]),([NSEW])\)([FLR]*)""".r

    sanitised match {
      case instructionPattern(maybePosition, maybeOrientation, maybeDirections) =>
        for {
          initialPosition <- Coordinate.fromString(maybePosition)
          orientation     <- Orientation.fromString(maybeOrientation)
          position = RobotPosition(initialPosition, orientation)
          instructions <- parseDirections(maybeDirections)
        } yield RobotInstructions(position, instructions)
      case _ => Left(RobotInstructionParsingError(str))
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
