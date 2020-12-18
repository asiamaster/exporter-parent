package com.dili;

import com.dili.ss.dto.DTOScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;

/**
 * 由MyBatis Generator工具自动生成
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.dili.ss","com.dili.exporter"})
@DTOScan(value={"com.dili.exporter.domain"})
@EnableDiscoveryClient
//@EnableFeignClients
public class Application extends SpringBootServletInitializer implements CommandLineRunner {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
//		maxMemory=Eden+Survivor+Old Gen
//		maxMemory是拿到的程序最大可以使用的内存，
//		我们知道 ，Survivor有两个，但只有1个会用到，另一个一直闲置。
//		所以这个值maxMemory是去掉一个Survivor空间的值。
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        System.out.println("maxMemory:" + decimalFormat.format(maxMemory>>10>>10) + "MB,totalMemory:" + decimalFormat.format(totalMemory>>10>>10) + "MB,freeMemory:" + decimalFormat.format(freeMemory>>10>>10)+"MB");
        System.out.println("项目启动完成!");
    }
}
