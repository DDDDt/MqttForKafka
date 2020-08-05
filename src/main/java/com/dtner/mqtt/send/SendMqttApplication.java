package com.dtner.mqtt.send;

/**
 * @ClassName SendMqttApplication
 * @Description: 发送信息到 mqtt
 * @Author dt
 * @Date 2020-08-05
 **/
public class SendMqttApplication {

    public static void main(String[] args) throws InterruptedException {

        SendMqttClient.init("123456");

        while (true){
            SendMqttClient.publishMessage("abc/up","test dt",1);
            System.out.println("发布信息...");
            Thread.sleep(10000L);
        }

    }

}
