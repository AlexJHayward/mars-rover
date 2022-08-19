package domain

import domain.Orientation._
import org.scalatest.EitherValues
import org.scalatest.matchers.must.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.wordspec.AnyWordSpec

class OrientationSpec
    extends AnyWordSpec
    with Matchers
    with TableDrivenPropertyChecks
    with EitherValues {

  "an orientation" should {
    "be rotatable 90º to the left" when {
      "it is facing north" in {
        Orientation.North.rotateLeft mustBe West
      }

      "it is facing east" in {
        Orientation.East.rotateLeft mustBe North
      }

      "it is facing south" in {
        Orientation.South.rotateLeft mustBe East
      }

      "it is facing west" in {
        Orientation.West.rotateLeft mustBe South
      }
    }

    "be rotatable 90º to the right" when {
      "it is facing north" in {
        Orientation.North.rotateRight mustBe East
      }

      "it is facing east" in {
        Orientation.East.rotateRight mustBe South
      }

      "it is facing south" in {
        Orientation.South.rotateRight mustBe West
      }

      "it is facing west" in {
        Orientation.West.rotateRight mustBe North
      }
    }
  }
}
