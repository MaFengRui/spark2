package SparkSql.DATASET

import org.apache.spark.sql.SparkSession

/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-15
  * Time:下午5:20
  * Vision:1.1
  **/
object CaseClass {
  //定义一个样例类并创建元素是吸纳dataset
  //dataset的创建
  case class  Number(i:Int,english:String,french:String)

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[2]").appName("test").getOrCreate()
    import spark.implicits._
    val numbers = Seq(
      Number(1, "one", "un"),
      Number(2, "two", "deux"),
      Number(3, "three", "trois")
    )
    val numerDS = numbers.toDS()
    println("样例类的类型如下")
    numerDS.dtypes.foreach(println(_))
    //我们可以对样例类的查询字段名
    numerDS.where($"i">2).select($"english",$"french").show()
    val dsNum = spark.createDataset(numbers)
    dsNum.dtypes.foreach(println)

  }

}
