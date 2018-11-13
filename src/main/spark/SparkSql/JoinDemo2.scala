package SparkSql

import org.apache.spark.sql.SparkSession

/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-7
  * Time:上午10:19
  * Vision:1.1
  **/
object JoinDemo2 {

  def main(args: Array[String]): Unit = {
    val session = SparkSession.builder().appName("RDD2DF3").master("local[2]").getOrCreate()
    import  session.implicits._
    session.conf.set("saprk.sql.autoBroadcastJoinThreshold",-1)
    session.conf.set("saprk.sql.join.preferSortMergeJoin",true)
    val df1 = Seq(
      (0,"tom"),
      (1,"tom1"),
      (2,"tom2")
    ).toDF("id","name")
    val df2 = Seq(
      (0,18),
      (1,20),
      (2,30)
    ).toDF("aid","age")
    df2.repartition()
    val res = df1.join(df2,$"aid"===$"id")
    res.explain()
    res.show()
    session.stop()
  }
}
