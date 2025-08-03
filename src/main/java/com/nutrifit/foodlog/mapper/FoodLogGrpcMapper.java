package com.nutrifit.foodlog.mapper;

import com.nutrifit.foodlog.dto.FoodLogRequest;
import com.nutrifit.foodlog.dto.FoodLogResponse;
import com.nutrifit.foodlog.v1.GrpcFoodLogResponse;

public class FoodLogGrpcMapper {

    public static GrpcFoodLogResponse toGrpcResponse(FoodLogResponse response) {
        return GrpcFoodLogResponse.newBuilder()
                .setFoodLogId(response.id().toString())
                .setUserId(response.userId().toString())
                .setDate(response.date().toString())
                .build();
    }
}
