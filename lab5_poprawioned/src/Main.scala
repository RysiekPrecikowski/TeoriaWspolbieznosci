import scala.language.postfixOps

object Main {
  def test1() = {
    val x = new MySymbol("x")
    val y = new MySymbol("y")
    val z = new MySymbol("z")

    val a = new Transaction(x, List(x, y), "a")
    val b = new Transaction(y, List(y, z), "b")
    val c = new Transaction(x, List(x, z), "c")
    val d = new Transaction(z, List(y, z), "d")

    val solution =
      new Solution(List(x, y, z), List(a, b, c, d), List(b, a, a, d, c, b))
  }

  def test2() = {
    val x = new MySymbol("x")
    val y = new MySymbol("y")
    val z = new MySymbol("z")
    val v = new MySymbol("v")

    val a = new Transaction(x, List(x, v), "a")
    val b = new Transaction(x, List(z, y), "b")
    val c = new Transaction(y, List(x, z), "c")
    val d = new Transaction(y, List(y, x, v), "d")
    val e = new Transaction(z, List(z), "e")
    val f = new Transaction(z, List(z, x, y), "f")
    val g = new Transaction(v, List(x, z), "g")
    val h = new Transaction(v, List(v, y), "h")

    val solution = new Solution(
      List(x, y, z),
      List(a, b, c, d, e, f, g, h),
      List(a, d, e, g, f, b, h, a)
    )
  }

  def main(args: Array[String]): Unit = {
    test1()

    test2()

  }
}
