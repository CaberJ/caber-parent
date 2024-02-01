package cn.caber.points.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "TestTopic", consumerGroup = "test-consumer-group")
@Slf4j
public class TestListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("接收到消息，{}", message);
    }
}
