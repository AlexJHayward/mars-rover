package domain

sealed trait MarsRoverError extends Exception

object MarsRoverError {
  case class RobotLost(lastKnownPosition: RobotPosition) extends MarsRoverError {
    override def getMessage: String = s"${lastKnownPosition.pretty} LOST"
  }

  case class GridParsingFailure(failedInput: String) extends MarsRoverError {
    override def getMessage: String =
      s"Could not parse grid dimensions. Dimensions must be in the format <width>x<height> where width and height are both positive integers. Failed input was \"$failedInput.\""
  }

  case class RobotInstructionParsingError(instructions: String) extends MarsRoverError {
    override def getMessage: String =
      s"Could not parse robot instructions. Must be in the form (initial x, initial y, orientation) instruction[]. Failed input was \"$instructions.\""
  }

  case class DirectionParsingError(directions: String) extends MarsRoverError {
    override def getMessage: String =
      s"Could not parse directions, must be one of [F,L,R]. directions were $directions"
  }

  case class CoordinateParsingError(coordinates: String) extends MarsRoverError {
    override def getMessage: String =
      s"Could not parse coordinates, must be two integers in the form x,y. coordinates were $coordinates"
  }

  case class OrientationParsingError(orientation: String) extends MarsRoverError {
    override def getMessage: String =
      s"Could not parse orientation, must be one of [N,E,S,W]. orientation was $orientation"
  }
}
