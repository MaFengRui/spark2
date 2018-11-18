package core

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-16
  * Time:上午8:24
  * Vision:1.1
  **/
object TopN {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[2]").appName("TopN").getOrCreate()
    val file = spark.sparkContext.textFile("/media/mafenrgui/办公/马锋瑞/spark2/testdata/TOPN")
    val value = file.flatMap(_.split(" ")).map(x=>{
      (x.toInt,null)
    }).sortByKey(false).take(10)
    value.foreach(println)
  }

}
