//package sparkStreaming
//
//import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
//import org.apache.spark.streaming.{Seconds, StreamingContext}
//import org.apache.spark.{SparkConf, SparkContext}
//
///**
//  *
//  * Created with IDEA
//  * author 光明顶斗士
//  * Date:18-11-9
//  * Time:上午11:04
//  * Vision:1.1
//  **/
//object sparkStreamingWc {
//  def main(args: Array[String]): Unit = {
//-
//    val sparkconf = new SparkConf().setAppName("Wc1").setMaster("local[2]")
//    val sc = new SparkContext(sparkconf)
//    //创建一个spark Streaming对象
//    val ssc:StreamingContext = new StreamingContext(sc,Seconds(10)) //第二个参数是批次的时间间隔
//    //第一个参数是服务的IP 第二个是监听的端口　　返回值是一个字符串，按行返回
//    val dStream: ReceiverInputDStream[String] = ssc.socketTextStream("192.168.37.83",8888)
//
//    val res: DStream[(String, Int)] = dStream.flatMap(_.split(" ")).map(((_,1))).reduceByKey(_+_)
//    //打印结果数据,每个RDD的前十个元素
//    res.print(10)
//    //提交任务到集群
//    ssc.start()
//    //等待处理下一次的任务
//
//    ssc.awaitTermination()
//
//  }
//
//}
