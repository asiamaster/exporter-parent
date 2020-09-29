# 通用导出器
使用方式:
spring boot配置
```properties
# 导出器的URL前缀(host:port)
exporter.contextPath=http://exporter.diligrp.com:8288
# 配置当前项目的URL前缀(host:port)
project.serverPath=http://uap.diligrp.com
# 最大导出等待时间，超时则发消息清空所有导出器的token.默认半小时
exporter.maxWait=1800000
```