
import example.BoardCalculator
import org.scalatest._

class BoardCalcSpec extends FlatSpec with Matchers {

  val testBoard =
  "Board Calculator" should "correctly move the player" in {
    BoardCalculator.calculateNextBoard()
  }

}
