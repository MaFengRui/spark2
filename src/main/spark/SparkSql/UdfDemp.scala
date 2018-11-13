//package SparkSql
//
//import org.apache.spark.sql.{Row, SparkSession}
//import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
//import org.apache.spark.sql.types._
//
///**
//  *
//  * Created with IDEA
//  * author 光明顶斗士
//  * Date:18-11-7
//  * Time:下午2:24
//  * Vision:1.1
//  **/
//object UdfDemo {
//  def main(args: Array[String]): Unit = {
//    val sparkSession = SparkSession.builder().appName("udfDemo").master("local[2]").getOrCreate()
//    val sparkContext = sparkSession.sparkContext
////    val rdd0 = sparkContext.parallelize(Array("tom","jerry","mary")).map()
//  }
//
//
//}
//class UDAF extends  UserDefinedAggregateFunction{
//  //输入的数据类型
//  override def inputSchema: StructType = ???
//  StructType(Array(StructField("count",StringType,true)))
//  //聚合运算，中间结果输出类型
//  override def bufferSchema: StructType = ???
//  StructType(Array(StructField("count",IntegerType,true)))
//  //最后聚合结果的返回类型
//  override def dataType: DataType = {
//    IntegerType
//  }
//  //数据的一致性
//  override def deterministic: Boolean = true
//  //初始化值，局部聚合使用（存放中间结果）
//  override def initialize(buffer: MutableAggregationBuffer): Unit ={
//    buffer(0)=0
//  }
//  //对每个分区数据怎么去聚合
//  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
//    buffer(0)=buffer.getAs[Int](0)+1
//  }
//  //聚合函数做全局聚合
//  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
//    buffer1(0)= buffer1.getAs[Int](0)+buffer2.getAs(0)
//
//  }
//  //返回聚合结果
//  override def evaluate(buffer: Row): Any = {
//    buffer.getAs[Int](0)
//  }
//}
