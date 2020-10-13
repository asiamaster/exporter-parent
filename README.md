# 通用导出器
使用方式:
应用程序spring boot配置
```properties
# 导出器的URL前缀(host:port)
exporter.contextPath=http://exporter.diligrp.com:8288
# 配置当前项目的URL前缀(host:port)
project.serverPath=http://uap.diligrp.com
```

导出服务spring boot配置
```properties
# 最大导出等待时间，超时则发消息清空所有导出器的token.默认半小时
exporter.maxWait=1800000
# 限制导出数，默认为4
exporter.limit=4
```

依赖包
```xml
<dependency>
    <groupId>com.dili</groupId>
    <artifactId>commons-web</artifactId>
    <version>1.3.4-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.dili</groupId>
    <artifactId>ss-beetl</artifactId>
    <version>3.6.8-SNAPSHOT</version>
</dependency>
```