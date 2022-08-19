import domain.Direction.Forward
import domain.MarsRoverError.RoverLost
import domain.Orientation.{North, South, West}
import domain._
import org.scalatest.EitherValues
import org.scalatest.matchers.must.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.wordspec.AnyWordSpec

class ProgramSpec
    extends AnyWordSpec
    with Matchers
    with TableDrivenPropertyChecks
    with EitherValues {

  "the program" should {
    "move a rover on a route within the grid" in {
      val grid = Grid(4, 8)

      // (2, 3, N) FLLFR
      val instruction = RoverInstructions(
        RoverPosition(Coordinate(2, 3), North),
        List(
          Forward,
          Direction.Left,
          Direction.Left,
          Forward,
          Direction.Right
        )
      )

      val result = Program.run(grid, List(instruction))

      result.length mustBe 1
      result.head.value mustBe RoverPosition(Coordinate(2, 3), West)
    }

    "leave a rover in one place that has no instructions" in {
      val grid = Grid(4, 8)

      // (2, 3, N) FLLFR
      val instruction = RoverInstructions(
        RoverPosition(Coordinate(2, 3), North),
        List.empty
      )

      val result = Program.run(grid, List(instruction))

      result.length mustBe 1
      result.head.value mustBe RoverPosition(Coordinate(2, 3), North)
    }

    "move a rover on a route where it is lost out of the grid" in {
      val grid = Grid(4, 8)

      // (1, 0, S) FFRLF
      val instruction = RoverInstructions(
        RoverPosition(Coordinate(1, 0), South),
        List(
          Forward,
          Forward,
          Direction.Right,
          Direction.Left,
          Forward
        )
      )

      val result = Program.run(grid, List(instruction))
      result.size mustBe 1
      result.head.left.value mustBe RoverLost(RoverPosition(Coordinate(1, 0), South))
    }
  }
}
