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


  def toDotFormat = {
    println("dot format:\n\n")
    println("digraph g{")

    var labels = ListBuffer.empty[String]

    for (i <- 0 until (graph.size)) {
      val first = graph(i)._1

      labels.addOne(first.name)

      for (k <- 0 until graph(i)._2.size) {
        val second = graph(i)._2(k)
        var found = false
        var j = i + 1
        while (!found) {
          if (j == graph.size){
            found = true
            println(i + " -> " + (j + k))
          } else if (second == graph(j)._1){
            println(i + " -> " + (j))
            found = true
          }
          j+=1
        }
      }
    }

    for (i <- 0 until(graph.last._2.size)){
      labels.addOne(graph.last._2(i).name)
    }

    for (i <- 0 until(labels.size)){
      println(i + "[label=" + labels(i) + "]")
    }

    println("}\n\n")
  }

}
