package com.nutrifit.foodlog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("grpc.client.user-account-service")
@Data
public class GrpcUserAccountServiceProperties {

    private String host;

    private Integer port;
}
