package kafka

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import kafka.utils.{ZKGroupDirs, ZKGroupTopicDirs, ZkUtils}
import org.I0Itec.zkclient.ZkClient
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Duration, StreamingContext}


/**
  *
  * Created with IDEA
  * author 光明顶斗士
  * Date:18-11-12
  * Time:下午5:02
  * Vision:1.1
  **/
object dirctDemo1{
  def main(args: Array[String]): Unit = {
        //指定组名
        val group = "g001"
        //创建sparkConf
        val conf = new SparkConf().setAppName("dirctDemo").setMaster("local[2]")
        //创建sparkStreaming 并设置间隔时间
        val ssc = new StreamingContext(conf,Duration(5000))
        //指定消费的topic 名字
        val topic = "test1"

        //指定kafka的broker地址（sparkStreaming的 task 直连到 kafka 的分区上，用更加底层的API消费，效率更高）
        val brokerList = "myspark:9092,myspark:9093,myspark:9094"
        //指定zk的地址，后期更新消费的偏移量时使用（以后可以使用redis，mysql来记录偏移量）
        val zkQuorum = "myspark:2181,myspark:2182,myspark:2183"
        //创建 stream 时使用的topic，名字的集合，sparkStreaming 可同时消费多个topic
        val topics:Set[String] = Set(topic)
        //创建一个ZKGroupTopicDirs 对象，其实是指定往zk中写入数据的目录保存偏移量
        val topicDirs = new ZKGroupTopicDirs(group,topic)
        //获取 zookeeper中的路径 'test1/offsets/wordcount/
        val zkTopicPath = s"${topicDirs.consumerOffsetDir}"

        //准备kafka的参数
        val kafkaParams = Map(
          "metadata.broker.list" -> brokerList,
          "group.id" -> group,
          //从头开始读取数据
          "auto.offset.reset" -> kafka.api.OffsetRequest.SmallestTimeString
        )
        //zookeeper的host 和 ip ,创建一个client,用于跟新偏移量的
        //是zookeeper的客户端，可以从zk中读取偏移量数据，并更新偏移量
        val zKClient: ZkClient =new ZkClient(zkQuorum)
        //查看该路径是否子节点（默认有子节点为我们自己保存不同partaition时生成的）
        val children = zKClient.countChildren(zkTopicPath)

        var kafkaStream:InputDStream[(String,String)] = null

        //如果 zookeeper 中保存offset，我们会利用这个offset作为kafkaStream的起始位置
        var fromOffsets:Map[TopicAndPartition,Long] = Map()
        //如果保存过offset
        if(children > 0) {
          //遍历每个分区
          for (i <- 0 until children) {
            val partitionOffset: String = zKClient.readData[String](s"$zkTopicPath/${i}")
            //wordcount/0
            val tp: TopicAndPartition = TopicAndPartition(topic, i)
            //将不同partition对应的offset增加到fromOffsets中
            //wordcount/0 -> 10001
            fromOffsets += (tp -> partitionOffset.toLong)
          }
          //key: kafka的key values:"hello tom hello jerry"
          //这个会将kafka 的消息进行 transform,最终kafka的数据都会变成（kafka的key，message）这样的tuple
          val messagerHander = (mmd: MessageAndMetadata[String, String]) => {
            (mmd.key(), mmd.message())
          }

          //通过kafkaUtil创建的直连Dsream(fromOffsets参数的作用是：按照前面计算好了的偏移量继续消费数据）
          //[String,String,StringDecoder,StringDecoder,(String)]
          //key value key的解码方式 value的解码方式
          kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (String, String)](ssc, kafkaParams, fromOffsets, messagerHander)

        }else{
          kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)
        }
        //便宜量的范围
        var offRange: Array[OffsetRange] = Array[OffsetRange]()
        //从kafka读取消息，DStream的TransFrom 可以将当前批次的RDD获取出来
        //从transform 方法 计算 获取到当前批次的RDD 然后将RDD的偏移量获取出来，然后将RDD返回到DStream
        val transfrom:DStream[(String,String)] = kafkaStream.transform{
          rdd =>
            //得到该RDD对应kafka的消息offset
            //该RDD是kafkaRDD 获取偏移量的范围
            offRange = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
            rdd
        }
        val message:DStream[String] = transfrom.map(_._2)
        //依次迭代DStream中的RDD
        message.foreachRDD{
          rdd=>
            rdd.foreachPartition(p =>
            p.foreach(x=>{
              println(x)
            }))
        }
        for (o <- offRange){
          //　/g001/offset
          val zkPath = s"${topicDirs.consumerOffsetDir}/${o.partition}"
          //将该partition的offset保存到zookeeper
          ZkUtils.updatePersistentPath(zKClient,zkPath,o.untilOffset.toString)

        }
        ssc.start()
        ssc.awaitTermination()

      }



}
