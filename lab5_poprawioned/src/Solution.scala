import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer
import scala.language.postfixOps
import scala.util.control.Breaks



class Solution(
    _symbols: List[MySymbol],
    _transactions: List[Transaction],
    _word: List[Transaction]
) {
  val transactions = _transactions
  val symbols = _symbols
  val word = _word




  var dependenceRelation = DependenceRelation(transactions)
  dependenceRelation.printD



  val fnf = new FNF
  fnf.calculateFromDependenceRelation(word, dependenceRelation)
  fnf.printFNF



  val graph = DiekertGraph(fnf, dependenceRelation)
  graph.printGraph




  val fnfFromGraph = new FNF
  fnfFromGraph.calculateFromDiekerGraph(graph)
  fnfFromGraph.printFNF
}
