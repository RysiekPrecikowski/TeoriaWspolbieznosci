import scala.collection.mutable.{ListBuffer, Map}

class FNF {
  val FNF = ListBuffer.empty[ListBuffer[Transaction]]
  
  
  def calculateFromDependenceRelation(word: List[Transaction], dependenceRelation: DependenceRelation) = {
    val matrix = dependenceRelation.matrix

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
  
  

  def printFNF: Unit = {
    print("FNF = ")
    for (l <- FNF) {
      print("[")
      for (el <- l) print(el.name + ", ")
      print("], ")
    }
    println
  }
  

}
