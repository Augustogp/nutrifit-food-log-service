package com.nutrifit.foodlog.adapter.grpc.client;

import com.nutrifit.foodlog.config.GrpcUserAccountServiceProperties;
import com.nutrifit.useraccount.v1.GrpcUserAccountResponse;
import com.nutrifit.useraccount.v1.UserAccountServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAccountGrpcService {

    private final GrpcExceptionHandler grpcExceptionHandler;

    private UserAccountServiceGrpc.UserAccountServiceBlockingStub blockingStub;

    private ManagedChannel channel;

    private final GrpcUserAccountServiceProperties properties;

    private String url;

    private Integer port;

    @PostConstruct
    public void init() {

        channel = ManagedChannelBuilder.forAddress(properties.getAddress(), properties.getPort()).usePlaintext().build();

        blockingStub = UserAccountServiceGrpc.newBlockingStub(channel);
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {

        if (blockingStub != null) {
            log.info("Shutting down gRPC UserAccountService stub");
            channel.shutdown().awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
        } else {
            log.warn("gRPC UserAccountService stub is already null");
        }
    }

    public GrpcUserAccountResponse getUserAccount(UUID userId) {
        log.info("Calling gRPC UserAccountService for userId: {}", userId);
        return grpcExceptionHandler.handle("User", userId, () -> blockingStub.getUserAccountById(com.nutrifit.useraccount.v1.GetUserAccountByIdRequest.newBuilder()
                .setUserId(userId.toString())
                .build()));
    }

}
