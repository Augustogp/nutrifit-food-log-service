package com.nutrifit.foodlog.mapper;

import com.nutrifit.foodlog.dto.FoodLogRequest;
import com.nutrifit.foodlog.dto.FoodLogResponse;
import com.nutrifit.foodlog.model.FoodLog;

public class FoodLogMapper {

    public static FoodLogResponse toResponse(FoodLog foodLog) {
        return new FoodLogResponse(
                foodLog.getId(),
                foodLog.getUserId(),
                foodLog.getDate()
        );
    }

    public static FoodLog toEntity(FoodLogRequest request) {
        return FoodLog.builder().
                userId(request.userId()).
                date(request.date()).
                build();
    }
}
