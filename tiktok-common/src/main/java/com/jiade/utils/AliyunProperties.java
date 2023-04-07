package com.jiade.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:AliyunProperties.properties")
@ConfigurationProperties(prefix = "aliyun")
public class AliyunProperties {

    private String secretId;
    private String secretKey;

}
