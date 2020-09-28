package com.dili.exporter.boot;

import com.dili.ss.sid.util.IdUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <B>RabbitMQ的相关配置信息</B>
 *
 * @author wangmi
 * @date 2020/9/28
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 交换机topic
     */
    public static final String MQ_EXPORTER_TOPIC_EXCHANGE = "dili.exporter.topicExchange";

    /**
     * 通知路由Queue
     */
    public static final String MQ_EXPORTER_ROUTING_QUEUE = "EXPORTER"+IdUtils.nextId();

    /**
     * 通知路由Key
     */
    public static final String MQ_EXPORTER_ROUTING_KEY = "dili.exporter.routingKey";

//    @Bean
//    public MessageConverter messageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }

    @Bean
    public TopicExchange exporterTopicExchange() {
        return new TopicExchange(MQ_EXPORTER_TOPIC_EXCHANGE, false, true);
    }

    @Bean
    public Queue routingQueue() {
        return new Queue(MQ_EXPORTER_ROUTING_QUEUE, false, false, true);
    }

    @Bean
    public Binding routingKeyBinding() {
        return BindingBuilder.bind(routingQueue()).to(exporterTopicExchange()).with(MQ_EXPORTER_ROUTING_KEY);
    }

}
