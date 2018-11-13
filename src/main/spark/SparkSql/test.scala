package SparkSql

import org.apache.spark.sql.SparkSession

/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-6
  * Time:下午12:48
  * Vision:1.1
  **/
object test {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("test").master("locak[2]").getOrCreate()
    sparkSession.sparkContext.textFile("")

  }

}
