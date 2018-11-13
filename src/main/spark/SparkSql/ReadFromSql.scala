package SparkSql

import org.apache.spark.sql.SparkSession

/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-6
  * Time:下午4:19
  * Vision:1.1
  **/
object ReadFromSql {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("test").master("locak[2]").getOrCreate()
    val res = sparkSession.read.format("jdbc").options(
      Map("url" -> "jdbc:mysql://192.168.37.83:3306/mfr",
        "driver" -> "com.mysql.jdbc.Driver",
        "dbtable" -> "answer_paper",
        "user" -> "root",
        "password" -> "123456")
    ).load()
    res.printSchema()
    res.show()
  }

}
