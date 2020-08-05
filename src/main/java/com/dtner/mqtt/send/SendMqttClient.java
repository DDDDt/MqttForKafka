package com.dtner.mqtt.send;

import com.dtner.mqtt.recieve.MqttRecieveCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @ClassName MqttClient
 * @Description: Mqtt 客户端
 * @Author dt
 * @Date 2020-08-05
 **/
public class SendMqttClient {

    private static MqttClient mqttClient = null;

    private static MemoryPersistence memoryPersistence = null;

    private static MqttConnectOptions mqttConnectOptions = null;

    private static String host = "tcp://";

    public static void init(String clientId){
        // 初始化连接设置对象
        mqttConnectOptions = new MqttConnectOptions();

        // true 可以安全地使用内存持久性作为客户端断开连接时清楚的所有状态
        mqttConnectOptions.setCleanSession(true);
        // 设置连接超时
        mqttConnectOptions.setConnectionTimeout(30);

        // 设置持久化方式
        memoryPersistence = new MemoryPersistence();

        try{
            // 初始化 MqttClient
            mqttClient = new MqttClient(host,clientId,memoryPersistence);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("mqtt 是否连接,"+mqttClient.isConnected());

        // 创建回调函数
        MqttRecieveCallback mqttRecieveCallback = new MqttRecieveCallback();
        // 客户端添加回调函数
        mqttClient.setCallback(mqttRecieveCallback);

        try{
            System.out.println("创建连接");
            mqttClient.connect(mqttConnectOptions);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 关闭连接
     */
    public static void closeConnect(){
        // 关闭存储方式
        if(memoryPersistence != null){
            try{
                memoryPersistence.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        // 关闭连接
        if(mqttClient != null){
            if(mqttClient.isConnected()){
                try{
                    mqttClient.disconnect();
                    mqttClient.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void publishMessage(String pubTopic, String message,int qos){

        if(mqttClient != null && mqttClient.isConnected()){

            System.out.println("发布消息："+mqttClient.isConnected());
            System.out.println("id: "+mqttClient.getClientId());
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setQos(qos);
            mqttMessage.setPayload(message.getBytes());

            MqttTopic topic = mqttClient.getTopic(pubTopic);

            if(topic != null){
                try{
                    MqttDeliveryToken publish = topic.publish(mqttMessage);
                    System.out.println("publish is completet "+publish.isComplete());
                    if(!publish.isComplete()){
                        System.out.println("消息发布成功");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }else{
            System.out.println("重新连接...");
            reConnect();
        }

    }

    // 重新连接诶
    public static void reConnect() {
        if (mqttClient != null) {
            if (!mqttClient.isConnected()) {
                if (mqttConnectOptions != null) {
                    try {
                        mqttClient.connect(mqttConnectOptions);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("mqttConnectOPtions is null");
                }
            } else {
                System.out.println("mqttClient is null or connect");
            }
        } else {
            init("123456");
        }
    }
}
