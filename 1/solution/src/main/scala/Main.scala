import scala.io.Source
import scala.collection.mutable.HashMap

object Main extends App {
  val lines = Source.fromFile("input.txt").getLines.toArray.map(_.toInt)

  def frequency1(input: Array[Int]) : Int = {
    return input.reduce(_ + _);
  }

  def frequency2(input: Array[Int]) : Int = {
    val cache: HashMap[Int, Boolean] = HashMap((0 -> true))
    var res: Int = 0
    while(true) {
      for(v <- input) {
        res += v
        if(cache contains res) {
          return res
        } else {
          cache += (res -> true)
        }
      }
    }
    return 1
  }


  println(frequency1(lines))
  println(frequency2(lines))
}
