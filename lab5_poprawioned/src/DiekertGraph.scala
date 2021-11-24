import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks

class DiekertGraph (fnf: FNF, dependenceRelation: DependenceRelation) {
  val fnf_list = fnf.FNF
  val matrix = dependenceRelation.matrix
  var graph = ListBuffer.empty[(Transaction, ListBuffer[Transaction])]


  for (i <- 0 until fnf_list.size - 1) {

    for (t1 <- fnf_list(i)) {
      var flag = false
      graph.addOne(t1, ListBuffer())


      Breaks.breakable {
        for (j <- i + 1 until fnf_list.size) {
          for (t2 <- fnf_list(j)) {
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
  def printGraph = {
    for ((t1, edges) <- graph) {
      print(t1.name + " - ")
      for (e <- edges) print(e.name + ", ")
      println
    }
  }
}
