package com.nutrifit.foodlog;

import com.nutrifit.foodlog.adapter.grpc.client.UserAccountGrpcService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class GrpcTestConfig {

    @Bean
    public UserAccountGrpcService userAccountGrpcService() {
        return Mockito.mock(UserAccountGrpcService.class);
    }
}
