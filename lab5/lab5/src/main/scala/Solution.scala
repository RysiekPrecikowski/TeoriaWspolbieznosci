import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer
import scala.language.postfixOps
import scala.util.control.Breaks

def printD(
    matrix: Map[Transaction, Map[Transaction, Boolean]],
    transactions: List[Transaction],
    D: ListBuffer[(Transaction, Transaction)],
    I: ListBuffer[(Transaction, Transaction)]
) = {
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

def printFNF(FNF: ListBuffer[ListBuffer[Transaction]]) = {
  print("FNF = ")
  for (l <- FNF) {
    print("[")
    for (el <- l) print(el.name + ", ")
    print("], ")
  }
  println
}

def printGraph(graph: ListBuffer[(Transaction, ListBuffer[Transaction])]) = {
  for ((t1, edges) <- graph) {
    print(t1.name + " - ")
    for (e <- edges) print(e.name + ", ")
    println
  }
}

class Solution(
    _symbols: List[MySymbol],
    _transactions: List[Transaction],
    _word: List[Transaction]
) {
  val transactions = _transactions
  val symbols = _symbols
  val word = _word

  var matrix: Map[Transaction, Map[Transaction, Boolean]] = Map()

  val D = ListBuffer.empty[(Transaction, Transaction)]
  val I = ListBuffer.empty[(Transaction, Transaction)]

  val FNF = ListBuffer.empty[ListBuffer[Transaction]]

  def dependenceRelation() = {
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
  }

  dependenceRelation()
  printD(matrix, transactions, D, I)

  def normalFoataForm() = {

    var used = List.fill(word.size)(false)

    for (i <- 0 until word.size - 1 if (!used(i))) {
      FNF += ListBuffer()

      var blocked = scala.collection.mutable.Set.empty[Transaction]
      val t1 = word(i)

      used = used.updated(i, true)

      val updateBlocked = (t1: Transaction) =>
        matrix(t1).foreach { case (t2, f) => if (f) blocked += t2 }

      updateBlocked(t1)
      FNF.last.addOne(t1)

      for (j <- i + 1 until word.size if (!used(j))) {
        val t2 = word(j)

        if (!blocked.contains(word(j))) {
          FNF.last.addOne(t2)
          used = used.updated(j, true)
        }

        updateBlocked(t2)
      }
    }

    if (!used(word.size - 1)) {
      FNF += ListBuffer()
      FNF.last.addOne(word(word.size - 1))
    }
  }

  normalFoataForm()

  printFNF(FNF)

  var graph = ListBuffer.empty[(Transaction, ListBuffer[Transaction])]
  def diekertGraph() = {

    for (i <- 0 until FNF.size - 1) {

      for (t1 <- FNF(i)) {
        var flag = false
        graph.addOne(t1, ListBuffer())
        Breaks.breakable {
          for (j <- i + 1 until FNF.size) {
            for (t2 <- FNF(j)) {
              if (matrix(t1)(t2)) {
                graph.last._2.addOne(t2)
                flag = true
              }
            }
            if (flag) Breaks.break
          }
        }
      }
    }
  }

  diekertGraph()

  printGraph(graph)

}
