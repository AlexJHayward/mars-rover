import domain.MarsRoverError.RoverLost
import domain._

import scala.annotation.tailrec

object Program {

  def run(
      grid: Grid,
      instructions: List[RoverInstructions]
  ): List[Either[MarsRoverError, RoverPosition]] = instructions.map(executeRoverMovement(_, grid))

  private def executeRoverMovement(
      roverInstructions: RoverInstructions,
      grid: Grid
  ): Either[MarsRoverError, RoverPosition] = {

    @tailrec
    def rec(
        currentPosition: RoverPosition,
        nextMovements: List[Direction]
    ): Either[MarsRoverError, RoverPosition] =
      nextMovements match {
        case Nil => Right(currentPosition)
        case thisMove :: nextMoves =>
          doMovement(thisMove, currentPosition, grid) match {
            case Left(err)    => Left(err)
            case Right(value) => rec(value, nextMoves)
          }
      }

    rec(roverInstructions.initialPosition, roverInstructions.directions)
  }

  private def doMovement(
      thisMove: Direction,
      currentPosition: RoverPosition,
      grid: Grid
  ): Either[MarsRoverError, RoverPosition] = {
    val newPosition = currentPosition.applyMovement(thisMove)

    if (grid.containsCoordinate(newPosition.coord)) Right(newPosition)
    else Left(RoverLost(currentPosition))
  }
}
