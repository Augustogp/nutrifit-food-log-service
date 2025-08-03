package com.nutrifit.foodlog.service;

import com.nutrifit.foodlog.adapter.grpc.client.UserAccountGrpcService;
import com.nutrifit.foodlog.exception.DuplicateResourceException;
import com.nutrifit.foodlog.mapper.FoodLogMapper;
import com.nutrifit.foodlog.dto.FoodLogRequest;
import com.nutrifit.foodlog.dto.FoodLogResponse;
import com.nutrifit.foodlog.exception.ResourceNotFoundException;
import com.nutrifit.foodlog.repository.FoodLogRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FoodLogService {

    private final FoodLogRepository foodLogRepository;
    private final UserAccountGrpcService userAccountGrpcService;

    public FoodLogResponse getFoodLogById(UUID id) {
        return foodLogRepository.findById(id)
                .map(FoodLogMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Food log not found with id: " + id));
    }

    public List<FoodLogResponse> getFoodLogsByUserId(UUID userId) {
        return foodLogRepository.findAllByUserId(userId)
                .stream()
                .map(FoodLogMapper::toResponse)
                .toList();
    }

    public FoodLogResponse getFoodLogByUserIdAndDate(UUID userId, LocalDate date) {
        return foodLogRepository.findByUserIdAndDate(userId, date)
                .map(FoodLogMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Food log not found for userId: " + userId + " and date: " + date));
    }

    public FoodLogResponse createFoodLog(FoodLogRequest foodLogRequest) {

        // TODO: Check if the userId actually exists in the user service
        userAccountGrpcService.getUserAccount(foodLogRequest.userId());

        if(foodLogRepository.existsByUserIdAndDate(foodLogRequest.userId(), foodLogRequest.date())) {
            throw new DuplicateResourceException("Food log already exists for userId: " + foodLogRequest.userId() + " and date: " + foodLogRequest.date());
        }

        var foodLog = FoodLogMapper.toEntity(foodLogRequest);
        var savedFoodLog = foodLogRepository.save(foodLog);
        return FoodLogMapper.toResponse(savedFoodLog);
    }

    public FoodLogResponse updateFoodLog(UUID id, FoodLogRequest foodLogRequest) {
        var existingFoodLog = foodLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food log not found with id: " + id));

        existingFoodLog.setUserId(foodLogRequest.userId());
        existingFoodLog.setDate(foodLogRequest.date());

        var updatedFoodLog = foodLogRepository.save(existingFoodLog);
        return FoodLogMapper.toResponse(updatedFoodLog);
    }

    public void deleteFoodLog(UUID id) {
        if (!foodLogRepository.existsById(id)) {
            throw new ResourceNotFoundException("Food log not found with id: " + id);
        }
        foodLogRepository.deleteById(id);
    }
}
