package SparkSql.DATASET

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-16
  * Time:上午12:00
  * Vision:1.1
  **/
object ComplexType {
  //for all the examples
  case class Point(x:Double,y:Double)
  //for example 1
  case class Segment(from:Point,to:Point)
  //for example 2
  case class Line(name:String,points:Array[Point])
  //for example 3
  case class NamePoints(name:String,point:Map[String,Point])
  //for example 4
  case class NameAndMaybePoint(name:String,point: Option[Point])

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[2]").appName("ComplexType").getOrCreate()

    import spark.implicits._

    println("*****Eample 1:neset case class")
    val segmentDS = Seq(
      Segment(Point(1.0, 2.0), Point(3.0, 4.0)),
      Segment(Point(8.0, 2.0), Point(3.0, 14.0)),
      Segment(Point(11.0, 2.0), Point(3.0, 24.0))
    ).toDS()
    segmentDS.printSchema()
    segmentDS.where($"from".getField("x") > 7.0).select($"to").show()

    //Example2 " arrrays
    println("********Example 2:arrays")

    val lines = Seq(
      Line("a", Array(Point(0.0, 0.0), Point(2.0, 4.0))),
      Line("b", Array(Point(-1.0, 0.0))),
      Line("c", Array(Point(2.0, 3.0), Point(4.0, 3.0), Point(2.0, 3.0), Point(2.0, 3.0)))
    )
    val linesDs = lines.toDS()
    linesDs.show()
    val frame: DataFrame = linesDs.toDF("name","points")
    frame.show()
    linesDs.printSchema()
//    println("***filter by an array element")
//    linesDs
//      .where($"points".getItem(2).getField("y") > 7.0)
//      .select($"name",size($"points").as("count")).show()

    //example4 option
    println("*******Example 4:Option")
    val maybePoints = Seq(
      NameAndMaybePoint("p1", None),
      NameAndMaybePoint("p2", Some(Point(1.2, 64))),
      NameAndMaybePoint("p3", Some(Point(1.2, 2.1)))
    ).toDS()
    maybePoints.where($"point".getField("y")>50)
      .select($"name",$"point").show()

  }

}
