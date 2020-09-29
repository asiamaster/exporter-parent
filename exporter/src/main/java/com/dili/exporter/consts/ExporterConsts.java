package com.dili.exporter.consts;

import com.dili.exporter.domain.ExportThread;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量定义
 * @description:
 * @author: WM
 * @time: 2020/9/28 16:02
 */

@Component
public class ExporterConsts {

    /**
     * 缓存导出中的token，和导出的线程
     */
    public static final Map<String, ExportThread> tokenCache = new HashMap<>();


}
