package com.dili.exporter.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dili.exporter.boot.RabbitMQConfig;
import com.dili.exporter.consts.ExporterConsts;
import com.dili.ss.constant.SsConstants;
import com.dili.ss.domain.ExportParam;
import com.dili.ss.mvc.controller.ExportController;
import com.dili.ss.mvc.util.ExportUtils;
import com.dili.ss.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;

/**
 * 导出器
 * Created by asiamaster on 2020/9/28
 */
@RefreshScope
@Controller
@RequestMapping("/exporter")
public class ExporterController {

    public final static Logger log = LoggerFactory.getLogger(ExportController.class);

    @Autowired
    ExportUtils exportUtils;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 限制导出数，默认为4
     */
    @Value("${exporter.limit:4}")
    private Integer limit;

    /**
     * 导出遮照的最大阻塞时间，默认半小时
     */
    @Value("${maxWait:1800000}")
    private Long maxWait;

    /**
     * 判断导出是否完成
     * @param request
     * @param response
     * @param token
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/isFinished.action")
    public @ResponseBody
    String isFinished(HttpServletRequest request, HttpServletResponse response, @RequestParam("token") String token) {
        ExporterConsts.tokenCache.put(token, Thread.currentThread());
        LockSupport.park();
        log.info("export token["+token+"] finished");
        return "true";
    }

    /**
     * 服务端导出
     *
     * @param request
     * @param response
     * @param columns
     * @param queryParams
     * @param title
     * @param url
     * @param contentType 默认为application/x-www-form-urlencoded
     * @param token
     */
    @RequestMapping("/serverExport.action")
    public @ResponseBody
    String serverExport(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam("columns") String columns,
                        @RequestParam("queryParams") String queryParams,
                        @RequestParam("title") String title,
                        @RequestParam("url") String url,
                        @RequestParam(name="contentType", required = false) String contentType,
                        @RequestParam("token") String token) {
        try {
            if(StringUtils.isBlank(token)){
                return "令牌不存在";
            }
            if(SsConstants.EXPORT_FLAG.size()>= SsConstants.LIMIT){
                //为避免isFinished方法中未成功清除token， 这里需要清空阻塞时间过长的Token
                for(Map.Entry<String, Long> entry : SsConstants.EXPORT_FLAG.entrySet()){
                    if(System.currentTimeMillis() >= (entry.getValue() + maxWait)){
                        SsConstants.EXPORT_FLAG.remove(entry.getKey());
                    }
                }
                SsConstants.EXPORT_FLAG.put(token, System.currentTimeMillis());
                return "服务器忙，请稍候再试";
            }
            exportUtils.export(request, response, buildExportParam(columns, queryParams, title, url, contentType));
            this.rabbitTemplate.convertAndSend(RabbitMQConfig.MQ_EXPORTER_TOPIC_EXCHANGE, RabbitMQConfig.MQ_EXPORTER_ROUTING_KEY, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构建导出参数
     * @param columns
     * @param queryParams
     * @param title
     * @param url
     * @param contentType
     * @return
     */
    private ExportParam buildExportParam(String columns, String queryParams, String title, String url, String contentType){
        ExportParam exportParam = new ExportParam();
        exportParam.setTitle(title);
        exportParam.setQueryParams((Map) JSONObject.parseObject(queryParams));
        exportParam.setColumns((List)JSONArray.parseArray(columns).toJavaList(List.class));
        exportParam.setUrl(url);
        exportParam.setContentType(contentType);
        return exportParam;
    }

}
