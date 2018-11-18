package SparkSql.DATASET

import org.apache.spark.sql.{Dataset, SparkSession}

/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-15
  * Time:下午4:19
  * Vision:1.1
  * 创建私有的类型类型dataset和元祖来显示简单的操作
  **/
object Basic {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[2]").appName("test").getOrCreate()
    import spark.implicits._
    //创建 tiny DataSet of 整型
    val s = Seq(10,44,45,54,45,45)
    val ds: Dataset[Int] = s.toDS()
    println("这仅是一列，他只有相同的名字")
    ds.columns.foreach(println(_))
    println("这是他的列类型")
    ds.dtypes.foreach(println(_))
    println("他的结构与dataFrame特别象")
    ds.printSchema()
    println("计算大于十二的值")
    ds.where($"value">12).show()


    val s2 = Seq.range(1,100)
    println("size of range")
    println(s2.size)

    val tuple1 = Seq((1,"a",true),(2,"a",false),(2,"a",false))
    val tpSet: Dataset[(Int, String, Boolean)] = tuple1.toDS()
    println("这个元组是dataset类型")
    tpSet.dtypes.foreach(println)
    tpSet.where($"_1">2).select($"_2",$"_3").show()

  }

}
