package com.dili.exporter.consts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常量定义
 * @description:
 * @author: WM
 * @time: 2020/9/28 16:02
 */
public class ExporterConsts {
    /**
     * 缓存导出中的token，和导出的线程
     */
    public static final Map<String, Thread> tokenCache = new HashMap<>();
}
