package SparkSql

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-6
  * Time:下午6:30
  * Vision:1.1
  *
  * 需求，根据ip找出所在的家
  **/
object Title2 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("ip").master("local[2]").getOrCreate()
    val ipProvice: RDD[String] = sparkSession.sparkContext.textFile("/media/mafenrgui/办公/马锋瑞/spark2/testdata/ip.txt")
    val provice = ipProvice.map(x => {
      val strings = x.split("\\|")
      val startIp = ip2Long(strings(0))
      val endIp = ip2Long(strings(1))
      val provice = strings(6)
      Row(startIp, endIp, provice)
    })
    val ipStructType: StructType = StructType(List(
      StructField("startIp", LongType),
      StructField("endIp", LongType),
      StructField("provice", StringType)
    ))

    val ipDataFrame = sparkSession.createDataFrame(provice,ipStructType)
    val t_1 = ipDataFrame.createTempView("t_1")

      import  sparkSession.implicits._
    val log: RDD[String] = sparkSession.sparkContext.textFile("/media/mafenrgui/办公/马锋瑞/spark2/testdata/http.log")
    val log1: RDD[Row] = log.map(x => {
      val strings = x.split("\\|")
      val l: Long = ip2Long(strings(1))
      Row(l)
    })
    val logStructType: StructType = StructType(List(
      StructField("userIp", LongType)
    ))

    val logDF = sparkSession.createDataFrame(log1,logStructType)
    val t_2 = logDF.createTempView("t_2")
    val res0: DataFrame = sparkSession.sql("select provice,count(1) from t_1 join t_2 on (startIp <= userIp and endIp >= userIp) group by provice")
    res0.show()

    sparkSession.stop()

  }
  def ip2Long(ip:String):Long={
    val ips = ip.split("\\.")
    var ipNum = 0L
    for(i <-0 until ips.length){

      ipNum = ips(i).toLong | ipNum << 8L

    }
    ipNum
  }

}
