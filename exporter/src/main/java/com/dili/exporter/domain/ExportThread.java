package com.dili.exporter.domain;

import java.time.LocalDateTime;

/**
 * 导出线程
 * @description:
 * @author: WM
 * @time: 2020/9/29 11:08
 */
public class ExportThread {
    //线程启动时间
    private LocalDateTime startTime;
    //导出线程
    private Thread thread;

    public ExportThread(LocalDateTime startTime, Thread thread) {
        this.startTime = startTime;
        this.thread = thread;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
