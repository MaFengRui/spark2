package kafka;

//import kafka.producer.Producer;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created with IDEA
 * author 光明顶斗士
 * Date:18-11-12
 * Time:上午10:07
 * Vision:1.1
 */
public class ProducerDemo{
    public static void main(String[] args) {
        //47.107.176.172
        //创建producer的配置信息
        Properties pro = new Properties();
        //指定消息发送到kafka集群
        pro.put("metadata.broker.list","47.107.176.172:9092");
        //指定消息的序列化方式
        pro.put("serializer.class", "kafka.serializer.StringEncoder");

        //封装配置信息=
        ProducerConfig config = new ProducerConfig(pro);
        //创建 producer
        Producer<String, String> producer = new Producer<>(config);
        for (int i = 0; i < 100; i++) {
            producer.send(new KeyedMessage<String, String>("test1","message"+i));
        }
    }

}
