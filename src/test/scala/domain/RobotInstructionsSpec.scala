package domain

import domain.Direction.Forward
import org.scalatest.EitherValues
import org.scalatest.matchers.must.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.wordspec.AnyWordSpec

class RobotInstructionsSpec
    extends AnyWordSpec
    with Matchers
    with TableDrivenPropertyChecks
    with EitherValues {

  "the parser" should {
    "parse robot instructions" when {
      "the instruction is valid" in {
        val Right(actual) = RobotInstructions.fromString("(0, 2, N) FFLFRFF")
        val expected = RobotInstructions(
          RobotPosition(Coordinate(0, 2), Orientation.North),
          List(Forward, Forward, Direction.Left, Forward, Direction.Right, Forward, Forward)
        )

        actual mustBe expected
      }

      "the instruction have no directions" in {
        val Right(actual) = RobotInstructions.fromString("(0, 2, N)")
        val expected = RobotInstructions(
          RobotPosition(Coordinate(0, 2), Orientation.North),
          List.empty
        )

        actual mustBe expected
      }
    }
  }
}
