package com.dtner.mqtt.recieve;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @ClassName ListenTopic
 * @Description: 监听 mqtt topic
 * @Author dt
 * @Date 2020-08-04
 **/
public class ListenTopic {

    private static int QoS = 1;

    /*mqtt ip 和端口号*/
    private static String Host = "tcp://";

    // 设置 clientId 的保存形式，默认是以内存保存
    private static MemoryPersistence memoryPersistence = null;

    // mqtt 的连接设置
    private static MqttConnectOptions mqttConnectOptions = null;

    private static MqttClient mqttClient = null;

    public static void init(String clientId){
        mqttConnectOptions = new MqttConnectOptions();
        memoryPersistence = new MemoryPersistence();
        try{
            // host 为主机名，clientId 即连接 mqtt 的客户端 id, 一般以唯一标识符标识
            mqttClient = new MqttClient(Host, clientId, memoryPersistence);
        }catch (Exception e){
            e.printStackTrace();
        }

        // 设置是否清空 session, 这里如果设置为 false 表示服务器会保留客户端的连接记录，这里设置微 true 表示每次连接到服务器都以新的身份连接
        mqttConnectOptions.setCleanSession(true);
        // 设置超时时间，单位为秒
        mqttConnectOptions.setConnectionTimeout(30);
        // 设置会话心跳时间，单位为秒，服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        mqttConnectOptions.setKeepAliveInterval(20);

        // 设置回调，client.setCallback就可以调用PushCallback类中的messageArrived()方法
        mqttClient.setCallback(new MqttRecieveCallback());

        try{
            mqttClient.connect();
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    public static void recieve(String topic){
        // 订阅消息
        int[] Qos = {QoS};
        String[] topics = {topic};
        if(null != mqttClient && mqttClient.isConnected()){
            try{
                mqttClient.subscribe(topics,Qos);
            }catch (MqttException e){
                e.printStackTrace();
            }
        }else {
            init("1234567");
            recieve(topic);
        }
    }
}
