package sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-9
  * Time:下午4:30
  * Vision:1.1
  **/
object windowsOp {
  def main(args: Array[String]): Unit = {
// val session= SparkSession.builder().master("local[2]").appName("windows").getOrCreate()
  val session = new SparkConf().setMaster("local[2]").setAppName("wd")
    val streamingContext = new StreamingContext(session,Seconds(5))

    streamingContext.checkpoint("/media/mafenrgui/办公/马锋瑞/ck01")
    val dstream = streamingContext.socketTextStream("192.168.37.83",8899)
    val tuple = dstream.flatMap(_.split(" ")).map((_,1))
      //设置窗口的长度，以及滑动的时间间隔
    val rews: DStream[(String, Int)] = tuple.reduceByKeyAndWindow((x: Int, y: Int) => {
      (x + y)
    }, Seconds(15), Seconds(10))
    rews.print()
    streamingContext.start()
    streamingContext.awaitTermination()

  }

}
