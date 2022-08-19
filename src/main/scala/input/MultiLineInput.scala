package input

import domain.MarsRoverError

import scala.annotation.tailrec
import scala.io.StdIn.readLine

object MultiLineInput {

  private val CancelCharacter = ""
  private val defaultReader   = (msg: String) => readLine(msg)

  def readLinesUntilEmpty[T](
      parser: String => Either[MarsRoverError, T],
      reader: String => String = defaultReader
  ): List[T] = {
    @tailrec
    def rec(acc: List[T]): List[T] = {
      val input = reader(">> ")

      parser(input) match {
        case _ if input == CancelCharacter => acc
        case Right(s)                      => rec(acc :+ s)
        case Left(err) =>
          println(err.getMessage)
          println("Please try again, or press enter to continue.")
          rec(acc)
      }
    }

    rec(List.empty)
  }
}
