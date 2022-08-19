package domain

import org.scalatest.EitherValues
import org.scalatest.matchers.must.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.wordspec.AnyWordSpec

class GridSpec extends AnyWordSpec with Matchers with TableDrivenPropertyChecks with EitherValues {

  "grid" should {

    "calculate if it contains a coordinate" when {
      val grid = Grid(10, 10)

      "the coordinate is inside the grid" in {
        grid.containsCoordinate(Coordinate(5, 5)) mustBe true
      }
      "the coordinate is on the edge of the grid" in {
        grid.containsCoordinate(Coordinate(0, 5)) mustBe true
      }
      "the coordinate is in the corner of the grid" in {
        grid.containsCoordinate(Coordinate(0, 0)) mustBe true
      }
      "the coordinate is outside the right-hand side of the grid" in {
        grid.containsCoordinate(Coordinate(15, 5)) mustBe false
      }
      "the coordinate is outside the left-hand side of the grid" in {
        grid.containsCoordinate(Coordinate(-5, 5)) mustBe false
      }
      "the coordinate is outside the top side of the grid" in {
        grid.containsCoordinate(Coordinate(5, 15)) mustBe false
      }
      "the coordinate is outside the bottom side of the grid" in {
        grid.containsCoordinate(Coordinate(5, -5)) mustBe false
      }
    }

    "parse from a string" when {
      "the grid is valid" when {
        val table = Table(
          ("input", "expected"),
          ("4 6", Grid(4, 6)),
          ("400 600", Grid(400, 600)),
          ("1 1", Grid(1, 1))
        )
        forAll(table) { (input, expected) =>
          s"grid=$input" in {
            val Right(result) = Grid.fromString(input)
            result mustBe expected
          }
        }
      }

      "the grid is invalid" when {

        def failureError(value: String) = MarsRoverError.GridParsingFailure(value)

        val table = Table(
          ("input", "expected"),
          ("blarg 6", failureError("blarg 6")),
          ("6 blarg", failureError("6 blarg")),
          ("1     1", failureError("1     1")),
          ("-1 -2", failureError("-1 -2")),
          ("0x4", failureError("0x4")),
          ("1     ", failureError("1     ")),
          ("     1", failureError("     1")),
          ("blarg", failureError("blarg"))
        )
        forAll(table) { (input, expected) =>
          s"grid=$input" in {
            val Left(err) = Grid.fromString(input)

            err.getMessage mustBe expected.getMessage
          }
        }
      }
    }
  }
}
