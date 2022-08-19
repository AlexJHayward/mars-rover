import domain.MarsRoverError.RobotLost
import domain._

import scala.annotation.tailrec

object Program {

  def run(
      grid: Grid,
      instructions: List[RobotInstructions]
  ): List[Either[MarsRoverError, RobotPosition]] = instructions.map(executeRobotMovement(_, grid))

  private def executeRobotMovement(
      robotInstructions: RobotInstructions,
      grid: Grid
  ): Either[MarsRoverError, RobotPosition] = {

    @tailrec
    def rec(
        currentPosition: RobotPosition,
        nextMovements: List[Direction]
    ): Either[MarsRoverError, RobotPosition] =
      nextMovements match {
        case Nil => Right(currentPosition)
        case thisMove :: nextMoves =>
          doMovement(thisMove, currentPosition, grid) match {
            case Left(err)    => Left(err)
            case Right(value) => rec(value, nextMoves)
          }
      }

    rec(robotInstructions.initialPosition, robotInstructions.directions)
  }

  private def doMovement(
      thisMove: Direction,
      currentPosition: RobotPosition,
      grid: Grid
  ): Either[MarsRoverError, RobotPosition] = {
    val newPosition = currentPosition.applyMovement(thisMove)

    if (grid.containsCoordinate(newPosition.coord)) Right(newPosition)
    else Left(RobotLost(currentPosition))
  }
}
