package SparkSql

import org.apache.spark.sql.{Dataset, SparkSession}

/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-6
  * Time:下午2:37
  * Vision:1.1
  **/
object sparkSqlWC {
  def main(args: Array[String]): Unit = {
    //创建sparkSession
    val sparkSession = SparkSession.builder().appName("sparksqlwc").master("local[2]").getOrCreate()
    val lines: Dataset[String] = sparkSession.read.textFile("/media/mafenrgui/办公/马锋瑞/spark2/testdata/person.txt")
    lines.show()
    import  sparkSession.implicits._
    val words: Dataset[String] = lines.flatMap(_.split(","))
//    words.show()
    val value = words.groupBy($"value").count().sort($"count"desc)
    import  org.apache.spark.sql.functions._
    val value1 = words.groupBy($"value" as "word").agg(count("*") as "count").orderBy($"count"desc)
//    value.show()
//    value1.show()

    words.createTempView("t_wc")
    val res0 = sparkSession.sql("SELECT value,COUNT(*) counts FROM t_wc GROUP BY value ORDER BY counts DESC")
    res0.show()

    sparkSession.stop()

  }

}
