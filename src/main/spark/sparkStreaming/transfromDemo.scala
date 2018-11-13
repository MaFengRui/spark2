//package sparkStreaming
//
//import org.apache.spark.SparkConf
//import org.apache.spark.rdd.RDD
//import org.apache.spark.streaming.dstream.DStream
//import org.apache.spark.streaming.{Seconds, StreamingContext}
//
///**
//  *
//  * Created with IDEA
//  * author 光明顶斗士
//  * Date:18-11-9
//  * Time:下午4:53
//  * Vision:1.1
//  * 过滤广告黑名单
//  **/
//object transfromDemo {
//  def main(args: Array[String]): Unit = {
//    val session = new SparkConf().setMaster("local[2]").setAppName("TransfromOP")
//    val streamingContext = new StreamingContext(session,Seconds(5))
//
//    //定义一个黑名单
//    val blackList = List("tom",true,("jerry",true))
//    val blackListRdd: RDD[Any] = streamingContext.sparkContext.parallelize(blackList)
//    //收到数据流
//    val socketData = streamingContext.socketTextStream("192.168.83.37",8899)
//    //解析出用户信息
//    val user: DStream[(String, String)] = socketData.map(line => {
//      (line.split("_")(1),line)
//    })
//    //
//    user.transform(u=>{
//      val joinrdd = u.leftOuterJoin()
//
//      j
//
//  }
//}
