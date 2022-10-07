import org.json4s._
import org.json4s.jackson.Json
import org.json4s.jackson.JsonMethods._

import java.io.PrintWriter
import scala.io.BufferedSource
import scala.io.Source.fromFile

object Main {
  def main(args: Array[String]): Unit = {
    def add_rating(line: Array[String]): List[Int] = {
      line(2) match {
        case "1" => List(line(1).toInt, 1, 0, 0, 0, 0)
        case "2" => List(line(1).toInt, 0, 1, 0, 0, 0)
        case "3" => List(line(1).toInt, 0, 0, 1, 0, 0)
        case "4" => List(line(1).toInt, 0, 0, 0, 1, 0)
        case "5" => List(line(1).toInt, 0, 0, 0, 0, 1)
        case _ => List(line(1).toInt, 0, 0, 0, 0, 0)
      }
    }

    def calc_sum(a1: List[Int], b1: List[Int]): List[Int] = {
      val res: List[Int] = List(-1,
        a1(1) + b1(1),
        a1(2) + b1(2),
        a1(3) + b1(3),
        a1(4) + b1(4),
        a1(5) + b1(5),
      )
      res
    }

    println("Lab01 App started")
    //    user id | item id | rating | timestamp
    val DATA_PATH = "/Users/dim/Desktop/SparkDE/lab01/u.data"
    val source: BufferedSource = fromFile(DATA_PATH)
    println(f"reading file $DATA_PATH")
    val lines: Seq[Array[String]] = source.getLines.toList.map(string => string.split("\t"))
    source.close()
    val lines_count = lines.length
    println(f"$lines_count readed")
    val lines_irf = lines.map(x => add_rating(x))
    //    println(lines_irf)
    //    for (line <- lines_irf) {
    //      for (o <- line) print(o+" ")
    //      println("")
    //    }
    val lines_sum = lines_irf.reduce((a1, b1) => calc_sum(a1, b1))
    println(lines_sum)
    val lines_202 = lines_irf.filter(_(0) == 202)
    //    println(lines_202)
    val lines_202_sum = lines_202.reduce((a1, b1) => calc_sum(a1, b1))
    println(lines_202_sum)
    val res_map = Map("hist_film" -> lines_202_sum.takeRight(5),
      "hist_all" -> lines_sum.takeRight(5))
    println(res_map)
    val json_res = Json(DefaultFormats).write(res_map)
    println(json_res)
    new PrintWriter("lab01.json") {
      write(json_res)
      close
    }
  }
}