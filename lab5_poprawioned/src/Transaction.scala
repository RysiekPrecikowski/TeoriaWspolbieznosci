class Transaction(_a: MySymbol, _b: List[MySymbol], _name: String = "") {
  val a = _a
  val b = _b
  val name = _name

  override def toString: String = {
    return name + ") " + a + " <- " + b
  }
}
