package com.nutrifit.foodlog.adapter.grpc.server;

import com.nutrifit.foodlog.dto.FoodLogResponse;
import com.nutrifit.foodlog.mapper.FoodLogGrpcMapper;
import com.nutrifit.foodlog.service.FoodLogService;
import com.nutrifit.foodlog.v1.FoodLogServiceGrpc;
import com.nutrifit.foodlog.v1.GetFoodLogByIdRequest;
import com.nutrifit.foodlog.v1.GetFoodLogByUserIdAndDateRequest;
import com.nutrifit.foodlog.v1.GrpcFoodLogResponse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDate;
import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class FoodLogGrpcService extends FoodLogServiceGrpc.FoodLogServiceImplBase {

    private final FoodLogService foodLogService;

    @Override
    public void getFoodLogById(GetFoodLogByIdRequest request, StreamObserver<GrpcFoodLogResponse> responseObserver) {

        log.info("getFoodLogById called with request: {}", request);

        GrpcFoodLogResponse response = FoodLogGrpcMapper.toGrpcResponse(foodLogService.getFoodLogById(UUID.fromString(request.getFoodLogId())));

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getFoodLogByUserIdAndDate(GetFoodLogByUserIdAndDateRequest request, StreamObserver<GrpcFoodLogResponse> responseObserver) {

        log.info("getFoodLogByUserIdAndDate called with request: {}", request);

        FoodLogResponse foodLogResponse = foodLogService.getFoodLogByUserIdAndDate(UUID.fromString(request.getUserId()), LocalDate.parse(request.getDate()));

        GrpcFoodLogResponse response = FoodLogGrpcMapper.toGrpcResponse(foodLogResponse);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
