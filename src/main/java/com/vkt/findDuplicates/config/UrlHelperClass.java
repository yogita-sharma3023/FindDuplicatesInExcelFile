package com.vkt.findDuplicates.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class UrlHelperClass {

	@Autowired
    private Environment env;

    @Value("${api.host}")
    private String host;

    public String getBaseUrl() {
        String port = env.getProperty("server.port", "8080"); // default 8080
        return "http://" + host + ":" + port;
    }
}
