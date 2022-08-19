package domain

import domain.Direction.Forward
import org.scalatest.EitherValues
import org.scalatest.matchers.must.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.wordspec.AnyWordSpec

class RoverPositionSpec
    extends AnyWordSpec
    with Matchers
    with TableDrivenPropertyChecks
    with EitherValues {

  "the rover" should {
    "move forward" in {
      val pos = RoverPosition(Coordinate(5, 5), Orientation.North)
      pos.applyMovement(Forward) mustBe RoverPosition(Coordinate(5, 6), Orientation.North)
    }
    "move left" in {
      val pos = RoverPosition(Coordinate(5, 5), Orientation.North)
      pos.applyMovement(Direction.Left) mustBe RoverPosition(Coordinate(5, 5), Orientation.West)
    }
    "move right" in {
      val pos = RoverPosition(Coordinate(5, 5), Orientation.North)
      pos.applyMovement(Direction.Right) mustBe RoverPosition(Coordinate(5, 5), Orientation.East)
    }
  }
}
