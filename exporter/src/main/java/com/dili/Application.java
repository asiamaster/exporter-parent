package com.dili;

import com.dili.ss.dto.DTOScan;
import com.dili.ss.retrofitful.annotation.RestfulScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 由MyBatis Generator工具自动生成
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.dili.ss","com.dili.exporter"})
@DTOScan(value={"com.dili.exporter.domain"})
@EnableDiscoveryClient
//@EnableFeignClients
public class Application extends SpringBootServletInitializer {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
