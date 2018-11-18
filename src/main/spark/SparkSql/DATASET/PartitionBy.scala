package SparkSql.DATASET

import java.io.File

import SparkSql.DATASET.PartitionBy.Transaction
import org.apache.spark.sql.SparkSession


/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-16
  * Time:下午7:51
  * Vision:1.1
  **/
object PartitionBy {
  case class Transaction(id:Long,year:Int,month:Int,quantity:Long,price:Double)

  def main(args: Array[String]): Unit = {

    //output goes herecd
    val exampleRoot = "/tmp/LearningSpark"
    val sparkSession = SparkSession.builder().appName("Dataset-PartitionBy").master("local[2]")
      //设置并行度
      .config("spark.default.parallelism", 12)
      .getOrCreate()

    SparkSql.utils.PartitionedTableHierarchy.deleteRecursively(new File(exampleRoot))
    import sparkSession.implicits._

    //24transactions
    val transactions = Seq(
      Transaction(1001, 2016, 10, 100, 42.99),
      Transaction(1002, 2016, 10, 100, 42.99),
      Transaction(1003, 2016, 10, 100, 42.99),
      Transaction(1004, 2016, 10, 100, 42.99),
      Transaction(1005, 2016, 10, 10, 42.99),
      Transaction(1006, 2016, 10, 100, 42.99),
      Transaction(1007, 2016, 9, 100, 42.99),
      Transaction(1008, 2016, 11, 100, 429),
      Transaction(1009, 2016, 11, 100, 42.99),
      Transaction(1010, 2016, 10, 100, 42.99),
      Transaction(1011, 2016, 10, 15, 42.99),
      Transaction(1012, 2016, 10, 100, 12.99),
      Transaction(1013, 2016, 10, 100, 42.99),
      Transaction(1014, 2016, 10, 100, 82.99),
      Transaction(1015, 2016, 10, 100, 92.99),
      Transaction(1016, 2016, 10, 10, 82.99),
      Transaction(1017, 2017, 10, 10, 42.99),
      Transaction(1018, 2017, 10, 100, 2.99),
      Transaction(1019, 2017, 10, 100, 42.99),
      Transaction(1020, 2017, 10, 100, 32.99),
      Transaction(1021, 2017, 11, 100, 12.99),
      Transaction(1022, 2017, 11, 155, 42.99),
      Transaction(1023, 2017, 11, 100, 42.99),
      Transaction(1024, 2017, 11, 100, 42.99)
    )
    val tDs = transactions.toDS()
    println("******number of partitions :"+tDs.rdd.partitions.size)
    val simpleRoot = exampleRoot+"/Simple"
    SparkSql.utils.PartitionedTableHierarchy.deleteRecursively(new File(simpleRoot))
    tDs.write
        .option("header",true)
      .csv(simpleRoot)
    println("*****simple output file count" + SparkSql.utils.PartitionedTableHierarchy.printRecursively(new File(simpleRoot)))
//    SparkSql.utils.PartitionedTableHierarchy.deleteRecursively(new File(simpleRoot))
    SparkSql.utils.PartitionedTableHierarchy.printRecursively(new File(simpleRoot))


  }

}
