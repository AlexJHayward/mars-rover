package input

import domain.MarsRoverError
import domain.MarsRoverError.CoordinateParsingError
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Try

class MultiLineInputSpec extends AnyWordSpec with Matchers {

  import MultiLineInputSpec._

  "the input reader" should {
    "process successive valid inputs" in {
      val inputs = List("1", "2", "3", CancelCharacter)
      val reader = new StatefulStubbedReader(inputs)

      val result = MultiLineInput.readLinesUntilEmpty[Int](numberParser, reader.reader)

      result mustBe List(1,2,3)
    }

    "process inputs even when one is invalid" in {
      val inputs = List("1", "2", "boom", "3", CancelCharacter)
      val reader = new StatefulStubbedReader(inputs)

      val result = MultiLineInput.readLinesUntilEmpty[Int](numberParser, reader.reader)

      result mustBe List(1, 2, 3)
    }

    "stob processing inputs after the cancel character" in {
      val inputs = List("1", "2", "3", CancelCharacter, "4", "5")
      val reader = new StatefulStubbedReader(inputs)

      val result = MultiLineInput.readLinesUntilEmpty[Int](numberParser, reader.reader)

      result mustBe List(1, 2, 3)
    }
  }
}

object MultiLineInputSpec {

  val CancelCharacter = ""

  val numberParser: String => Either[MarsRoverError, Int] = (input: String) =>
    Try(input.toInt).toOption.toRight(CoordinateParsingError("boom"))

  class StatefulStubbedReader(inputs: List[String]) {

    private var l: List[String] = inputs

    def reader: String => String = _ => {
      val next = l.head
      l = l.tail
      next
    }
  }
}
