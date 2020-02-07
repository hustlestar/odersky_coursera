package recfun

object RecFun extends RecFunInterface {

  def main(args: Array[String]): Unit = {
        println("Pascal's Triangle")
        for (row <- 0 to 10) {
          for (col <- 0 to row)
            print(s"${pascal(col, row)} ")
          println()
        }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1 else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    @scala.annotation.tailrec
    def innerBracketIter(openBracketsCount: Int, part: List[Char]): Boolean = {
      if (part.isEmpty) {
        openBracketsCount == 0
      } else if (openBracketsCount == 0 && part.head == ')') {
        false
      } else if (part.head == '(') {
        innerBracketIter(openBracketsCount + 1, part.tail)
      } else if (part.head == ')') {
        innerBracketIter(openBracketsCount - 1, part.tail)
      } else {
        innerBracketIter(openBracketsCount, part.tail)
      }
    }

    innerBracketIter(0, chars)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = coins match {
    case Nil => 0
    case head :: tail =>
      if (head > money) countChange(money, tail)
      else if (money - head == 0) 1 + countChange(money, tail)
      else countChange(money - head, coins) + countChange(money, tail)
  }

}
