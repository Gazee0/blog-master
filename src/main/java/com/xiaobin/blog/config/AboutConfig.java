package com.xiaobin.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@PropertySource("classpath:about.properties")
@ConfigurationProperties(prefix = "about")
public class AboutConfig {
    private String qq;
    private String wechat;
    private String wechatPay;
    private String aliPay;
    private String introduce;
    private String github;
    private String picture;
    private String email;
    private List<String> hobby;
    private List<String> tag;
    private String blogMessage;
    private String websiteName;
    private String author;
}
