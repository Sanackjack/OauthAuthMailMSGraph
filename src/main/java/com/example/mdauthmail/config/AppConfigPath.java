package com.example.mdauthmail.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Getter
@Configuration
public class AppConfigPath {

    @Value("${mail.sender}")
    private String mailSender;


    @Value("#{'${mail.recipient}'.split(';')}")
    private List<String> mailReceipt;

    @Value("${mail.scope}")
    private String mailScope;


}
