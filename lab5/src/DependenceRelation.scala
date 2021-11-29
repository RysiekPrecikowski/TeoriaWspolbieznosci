import scala.collection.mutable.{ListBuffer, Map}

class DependenceRelation(transactions: List[Transaction]) {
  var matrix: Map[Transaction, Map[Transaction, Boolean]] = Map()

  val D = ListBuffer.empty[(Transaction, Transaction)]
  val I = ListBuffer.empty[(Transaction, Transaction)]


  for (transaction <- transactions) {
    matrix += (transaction, Map())

    for (t <- transactions) matrix(transaction)(t) = false

    matrix(transaction)(transaction) = true
  }

  for (i <- 0 until transactions.size - 1) {

    for (j <- i + 1 until transactions.size) {
      if (
        transactions(i).b.contains(transactions(j).a) || transactions(j).b
          .contains(transactions(i).a)
      ) {
        matrix(transactions(i))(transactions(j)) = true
        matrix(transactions(j))(transactions(i)) = true
        D.addOne((transactions(i), transactions(j)))
      } else {
        I.addOne((transactions(i), transactions(j)))
      }
    }
  }


  def printD = {
    val cup = 0x222a.toChar

    print("  ")
    for (transaction <- transactions) {
      print(transaction.name + " ")
    }
    println
    for (transaction <- transactions) {
      print(transaction.name + " ")

      for (t <- transactions) {
        val x = if (matrix(transaction)(t)) 1 else 0

        print(x + " ")

      }
      println
    }
    print("D = { sym { ")
    D.foreach { case (t1, t2) => print("(" + t1.name + ", " + t2.name + "), ") }
    println("}} " + cup + " I_A")

    print("I = { sym { ")
    I.foreach { case (t1, t2) => print("(" + t1.name + ", " + t2.name + "), ") }
    println("}}")
  }
}
