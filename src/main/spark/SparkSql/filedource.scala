package SparkSql

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-6
  * Time:下午4:04
  * Vision:1.1
  **/
object filesource {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("filesource").master("local[2]").getOrCreate()
    //读取json文件
    import  sparkSession.implicits._
    val json: DataFrame = sparkSession.read.json("")
//    val json1 = sparkSession.read.format("json").load("")

//    val csv: DataFrame = sparkSession.read.csv("")
//    val parquet: DataFrame = sparkSession.read.parquet("")

    val jsons = json.where($"age" <= 30)
    jsons.printSchema()
    //存文件
    jsons.write.csv("")
    jsons.write.json("")
    jsons.write.text("")
    jsons.show()
  }

}
