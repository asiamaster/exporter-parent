package com.dili.exporter.component;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: WM
 * @time: 2020/9/24 10:03
 */
@Component
public class ServerCustomizationBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(8081);
    }
}
