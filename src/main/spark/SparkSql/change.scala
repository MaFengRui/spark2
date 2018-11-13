//package SparkSql
//
//import org.apache.spark.rdd.RDD
//import org.apache.spark.sql.SparkSession
//
///**
//  *
//  * Created with IDEA
//  * author 光明顶斗士
//  * Date:18-11-6
//  * Time:下午3:20
//  * Vision:1.1
//  **/
//object change {
//
//  def main(args: Array[String]): Unit = {
//    //dataset dataframe转RDD
//    val sc = SparkSession.builder().appName("transfrom").master("local[2]").getOrCreate()
//    val rdd: RDD[String] = sc.read.textFile("/media/mafenrgui/办公/马锋瑞/spark2/testdata/person.txt").rdd
//    import  sc.implicits._
//
//    rdd.map(line => (line,1)).toDF("column1","column2")
//
//    //rdd转换成dataset
//
//    case  class column(col1:String,col2:Int) extends Serializable
//    val ds = rdd.map(x => column(x,1)).toDS()
//    val df = ds.toDF()
//    case  class column1(col1:String,col2:Int) extends Serializable
//    val ds2 = df.as[column1]
//  }
//
//}
