package SparkSql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession


/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-9
  * Time:下午4:04
  * Vision:1.1
  *
  *
  **/
object test1 {
  def main(args: Array[String]): Unit = {
    val session: SparkSession = SparkSession.builder().appName("test").master("local[2]").getOrCreate()
    val file: RDD[String] = session.sparkContext.textFile("/media/mafenrgui/办公/马锋瑞/spark2/testdata/test.txt")
    val tupe: RDD[(Int, String, String)] = file.map(x => {
      val strings = x.split("\t")
      (strings(0).toInt, strings(1), strings(2))
    })
    
    val unit: RDD[(Int, String, String)] = tupe.sortBy(x=> (x._1,x._2))
    val tuples = unit.collect()

    tuples.foreach(println)
  }
}
