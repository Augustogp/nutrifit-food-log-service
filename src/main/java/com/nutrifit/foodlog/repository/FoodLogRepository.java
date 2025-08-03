package com.nutrifit.foodlog.repository;

import com.nutrifit.foodlog.model.FoodLog;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FoodLogRepository extends JpaRepository<FoodLog, UUID> {
    List<FoodLog> findAllByUserId(UUID userId);

    Optional<FoodLog> findByUserIdAndDate(UUID userId, LocalDate date);

    boolean existsByUserIdAndDate(UUID userId, LocalDate date);
}

