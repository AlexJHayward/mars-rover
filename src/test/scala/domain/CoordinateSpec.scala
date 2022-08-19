package domain

import org.scalatest.EitherValues
import org.scalatest.matchers.must.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.wordspec.AnyWordSpec

class CoordinateSpec
    extends AnyWordSpec
    with Matchers
    with TableDrivenPropertyChecks
    with EitherValues {

  "a coordinate" should {
    "be movable forward" when {
      "the orientation is north" in {
        val coord = Coordinate(5,5)
        val result = coord.moveForward(Orientation.North)

        result mustBe Coordinate(5,6)
      }
      "the orientation is east" in {
        val coord = Coordinate(5, 5)
        val result = coord.moveForward(Orientation.East)

        result mustBe Coordinate(6, 5)
      }
      "the orientation is south" in {
        val coord = Coordinate(5, 5)
        val result = coord.moveForward(Orientation.South)

        result mustBe Coordinate(5, 4)
      }
      "the orientation is west" in {
        val coord = Coordinate(5, 5)
        val result = coord.moveForward(Orientation.West)

        result mustBe Coordinate(4, 5)
      }
    }
  }
}
