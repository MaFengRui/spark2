package kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IDEA
 * author 光明顶斗士
 * Date:18-11-12
 * Time:上午10:21
 * Vision:1.1
 */
public class ConsumerDemo {
    //指定消费的主题
    private static final String topic = "test1";
    //指定消费者的线程个数
    private static final Integer threads = 2;

    public static void main(String[] args) {
        // 创建消费者的配置信息
        Properties pro = new Properties();
        //指定zookeeper的位置
        pro.put("zookeeper.connect", "47.107.176.172:2181,47.107.176.172:2182,47.107.176.172:2183");
        // 指定消费者族的信息
        pro.put("group.id", "testGroup");
        //配置消费消息的开始位置,smalllest代表最开始的位置进行消费
        //largest:代表消费者连接上的该topic行产生的消息开始消费
        pro.put("auto.offset.reset", "smallest");
        ConsumerConfig config = new ConsumerConfig(pro);
        //创建消费者
        ConsumerConnector connector = Consumer.createJavaConsumerConnector(config);
        HashMap<String, Integer> topicHashMap = new HashMap<>();
        topicHashMap.put(topic, threads);
        //创建信息流
        Map<String, List<KafkaStream<byte[], byte[]>>> map = connector.createMessageStreams(topicHashMap);
        //获取topic信息
        List<KafkaStream<byte[], byte[]>> kafkaStreamList = map.get(topic);
        //一直循环拉去取消息
        for (final KafkaStream<byte[],byte[]> kafkaStream :kafkaStreamList){
            //
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //循环读取每一条消息
                    for (MessageAndMetadata<byte[],byte[]> msg :kafkaStream) {
                        String message = new String(msg.message());
                        System.out.println(message);

                    }
                }
            }).start();

        }
    }
}

