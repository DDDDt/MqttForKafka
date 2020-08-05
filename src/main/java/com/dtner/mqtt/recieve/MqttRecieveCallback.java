package com.dtner.mqtt.recieve;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @ClassName MqttRecieveCallback
 * @Description: mqtt recieve callback
 * @Author dt
 * @Date 2020-08-04
 **/
public class MqttRecieveCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {
    }


@Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Client 接受消息主题："+topic);
        System.out.println("Client 接受消息 Qos : "+message.getQos());
        System.out.println("Client 接受消息内容 : " + new String(message.getPayload(),"UTF-8"));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

}
