package domain

import domain.Direction.Forward
import org.scalatest.EitherValues
import org.scalatest.matchers.must.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.wordspec.AnyWordSpec

class RobotPositionSpec
    extends AnyWordSpec
    with Matchers
    with TableDrivenPropertyChecks
    with EitherValues {

  "the robot" should {
    "move forward" in {
      val pos = RobotPosition(Coordinate(5, 5), Orientation.North)
      pos.applyMovement(Forward) mustBe RobotPosition(Coordinate(5, 6), Orientation.North)
    }
    "move left" in {
      val pos = RobotPosition(Coordinate(5, 5), Orientation.North)
      pos.applyMovement(Direction.Left) mustBe RobotPosition(Coordinate(5, 5), Orientation.West)
    }
    "move right" in {
      val pos = RobotPosition(Coordinate(5, 5), Orientation.North)
      pos.applyMovement(Direction.Right) mustBe RobotPosition(Coordinate(5, 5), Orientation.East)
    }
  }
}
