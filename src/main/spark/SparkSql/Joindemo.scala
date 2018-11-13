package SparkSql

import org.apache.spark.sql.{Dataset, SparkSession}

/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-7
  * Time:上午9:51
  * Vision:1.1
  **/
object Joindemo {
  def main(args: Array[String]): Unit = {
    val session = SparkSession.builder().appName("RDD2DF3").master("local[2]").getOrCreate()
    import  session.implicits._
    val lines: Dataset[String] = session.createDataset(Array("1,hanmeimei,china","2,tom,china","3,jor,english"))
    //数据整理
    val tuples = lines.map(x => {
      val fields = x.split(",")
      val id = fields(0).toLong
      val name = fields(1).toString
      val country = fields(2)
      (id, name, country)
    })
    val df1 = tuples.toDF("id","name","country")
    val lines1: Dataset[String] = session.createDataset(List("china,中国","english,美国"))

    val tupleDF2 = lines1.map(x => {
      val fields = x.split(",")
      val ename = fields(0)
      val cname = fields(1)
      (ename, cname)
    })
    val df2 = tupleDF2.toDF("ename","cname")
    //创建视图
    df1.createTempView("t_users")
    df2.createTempView("t_countrys")
    val res = session.sql("SELECT id,name,cname FROM t_users JOIN t_countrys ON country = ename")
    res.show()
    res.explain()
    session.stop()

  }

}
