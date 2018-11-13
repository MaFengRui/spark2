package kafka

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-12
  * Time:上午11:45
  * Vision:1.1
  **/
object SparkStreamingKafkaWCC {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SparkStreamingKafkaWC").setMaster("local[2]");
    val ssc = new StreamingContext(conf,Seconds(5));
    //指定整合的kafka相关的参数
    val zkQueue = "myspark:2181,myspark:2182,myspark:2183"
    val groupId = "group1"
    val topic = Map[String,Int]("test1" ->1)

    //连接broker消费数据
    //第一个String表示写入消息的key值，第二个String代表真正的消息内容
    val data:ReceiverInputDStream[(String,String)] = KafkaUtils.createStream(ssc,zkQueue,groupId,topic)

    val lines = data.map(_._2)
    val words = lines.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    words.print()
    ssc.start()
    ssc.awaitTermination()
  }

}
