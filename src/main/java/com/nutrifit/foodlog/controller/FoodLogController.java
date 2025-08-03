package com.nutrifit.foodlog.controller;

import com.nutrifit.foodlog.dto.FoodLogRequest;
import com.nutrifit.foodlog.dto.FoodLogResponse;
import com.nutrifit.foodlog.service.FoodLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/food-logs")
@RequiredArgsConstructor
public class FoodLogController {

    public final FoodLogService foodLogService;

    @GetMapping("{id}")
    public ResponseEntity<FoodLogResponse> getFoodLogById(UUID id) {
        FoodLogResponse foodLogResponse = foodLogService.getFoodLogById(id);
        return ResponseEntity.ok(foodLogResponse);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FoodLogResponse>> getFoodLogsByUserId(@PathVariable UUID userId) {
        List<FoodLogResponse> foodLogResponseList = foodLogService.getFoodLogsByUserId(userId);
        return ResponseEntity.ok(foodLogResponseList);
    }

    @GetMapping("/user/{userId}/date/{date}")
    public ResponseEntity<FoodLogResponse> getFoodLogByUserIdAndDate(@PathVariable UUID userId, @PathVariable LocalDate date) {
        FoodLogResponse foodLogResponse = foodLogService.getFoodLogByUserIdAndDate(userId, date);
        return ResponseEntity.ok(foodLogResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<FoodLogResponse> updateFoodLog(@PathVariable UUID id, @RequestBody FoodLogRequest foodLogRequest) {
        FoodLogResponse foodLogResponse = foodLogService.updateFoodLog(id, foodLogRequest);
        return ResponseEntity.ok(foodLogResponse);
    }

    @PostMapping
    public ResponseEntity<FoodLogResponse> createFoodLog(@RequestBody FoodLogRequest foodLogRequest) {
        FoodLogResponse foodLogResponse = foodLogService.createFoodLog(foodLogRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(foodLogResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFoodLog(@PathVariable UUID id) {
        foodLogService.deleteFoodLog(id);
        return ResponseEntity.noContent().build();
    }

}
