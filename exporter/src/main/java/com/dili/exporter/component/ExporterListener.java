package com.dili.exporter.component;

import com.dili.exporter.consts.ExporterConsts;
import com.dili.exporter.domain.ExportThread;
import com.dili.ss.util.NetworkUtils;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.LockSupport;

/**
 * <B>路由MQ消息监听器</B>
 *
 * @author wm
 * @date 2020/9/23
 */
@Component
public class ExporterListener implements ApplicationListener<WebServerInitializedEvent> {

    protected static final Logger log = LoggerFactory.getLogger(ExporterListener.class);
    private int serverPort;

    public int getPort() {
        return this.serverPort;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }
    /**
     * 路由同步消息监听器
     * @param message
     * @throws Exception
     */
    @RabbitListener(queues = "#{rabbitMQConfig.MQ_EXPORTER_ROUTING_QUEUE}")
    public void synchronousListener(Channel channel, Message message) {
        try {
            String data = new String(message.getBody(), "UTF-8");
            //因为获取的data两边有引号，所以需要用substring去掉
            if(data.startsWith("\"")) {
                data = data.substring(1, data.length() - 1).trim();
            }
            if(ExporterConsts.tokenCache.containsKey(data)){
                log.info("["+NetworkUtils.getLocalIP()+":"+serverPort+"]处理导出token: " + data);
                ExportThread exportThread = ExporterConsts.tokenCache.get(data);
                ExporterConsts.tokenCache.remove(data);
                LockSupport.unpark(exportThread.getThread());
            }
        } catch (Exception ex) {
            log.error("消息 {} 处理失败 {}", message, ex);
        }
    }

}
