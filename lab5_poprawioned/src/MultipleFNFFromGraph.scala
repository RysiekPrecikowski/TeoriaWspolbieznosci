import scala.collection.mutable.ListBuffer

class MultipleFNFFromGraph(graph: DiekertGraph) {
  var list = ListBuffer.empty[FNF]

  var q = Set.empty[Transaction]

  



  def printFNFs = for(fnf <- list) fnf.printFNF
}
