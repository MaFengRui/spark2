package SparkSql

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._

object RDD2DF3 {
  def main(args:Array[String]):Unit={
    //spark2.x SQL执行的入口
    val session = SparkSession.builder().appName("RDD2DF3").master("local[2]").getOrCreate()

    //创建RDD
    val lines = session.sparkContext.textFile("/media/mafenrgui/办公/马锋瑞/spark2/testdata/person.txt")

    //数据整理
    val rowRDD = lines.map(line =>{

      val fields = line.split(",")
      val id = fields(0).toLong
      val name = fields(1)
      val age = fields(2).toInt
      val facevalue = fields(3).toDouble
      Row(id,name,age,facevalue)
    })

    //定义一个schema
    val schema:StructType = StructType(List(
      StructField("id",LongType,true),
      StructField("name",StringType,true),
      StructField("age",IntegerType,true),
      StructField("facevalue",DoubleType,true)

    ))

    //创建一个DataFrame
    val df = session.createDataFrame(rowRDD,schema)
    df.rdd
    import session.implicits._
    val res = df.where($"age" > 30).orderBy($"name")
    res.show()
    session.stop()
  }

}
