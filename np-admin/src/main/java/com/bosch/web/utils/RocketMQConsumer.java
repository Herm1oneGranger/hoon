package com.bosch.web.utils;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
//@RocketMQMessageListener(topic = "my-topic",   consumerGroup = "my-consumer-group")
// implements RocketMQListener<String>
public class RocketMQConsumer{

//    @Override
//    public void onMessage(String message) {
//        System.out.println("Received message: " + message);
//    }
}
