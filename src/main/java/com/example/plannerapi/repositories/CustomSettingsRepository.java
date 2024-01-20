package com.example.plannerapi.repositories;

import com.example.plannerapi.domain.entities.CustomSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CustomSettingsRepository extends JpaRepository<CustomSettingsEntity, Long> {
}