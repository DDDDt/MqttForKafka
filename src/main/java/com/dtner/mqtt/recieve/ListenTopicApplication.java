package com.dtner.mqtt.recieve;

/**
 * @ClassName ListenTopicApplication
 * @Description: 监听 mqtt topic
 * @Author dt
 * @Date 2020-08-04
 **/
public class ListenTopicApplication {

    public static void main(String[] args) {

        System.out.println("开始接收信息");
        ListenTopic.recieve("abc/up");

    }

}
