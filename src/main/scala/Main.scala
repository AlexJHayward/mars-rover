import domain.{Grid, MarsRoverError, RoverInstructions, RoverPosition}
import input.MultiLineInput

import scala.io.StdIn.readLine

object Main {
  def main(args: Array[String]): Unit = {
    println("\uD83E\uDD16 \uD83E\uDD16 Welcome to the Mars Rover Navigation System \uD83E\uDD16 \uD83E\uDD16")

    val gridMessage =
      "Please enter your desired grid size (in the form <Width> <Height>, i.e. 4 6) >>"
    val positionMessage =
      "Please input the initial starting positions of your rovers, each on a new line. When you are ready to submit, please hit enter again on an empty line."

    val output: Either[MarsRoverError, List[Either[MarsRoverError, RoverPosition]]] = for {
      gridInput <- Right(readLine(gridMessage))
      grid      <- Grid.fromString(gridInput)
      _ = println(positionMessage)
      rovers <- Right(MultiLineInput.readLinesUntilEmpty(RoverInstructions.fromString))
    } yield Program.run(grid, rovers)

    output.fold(
      err => println(s"There was a failure parsing your input, error was: ${err.getMessage}"),
      pos =>
        println(
          pos
            .map {
              case Left(err)     => err.getMessage
              case Right(result) => result.pretty
            }
            .mkString("\n")
        )
    )
  }
}
