package com.example.plannerapi.repositories;

import java.util.List;
import java.util.Optional;

import com.example.plannerapi.domain.entities.TokenRedisEntity;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<TokenRedisEntity, String> {

    Optional<TokenRedisEntity> getByToken(String token);
    Optional<TokenRedisEntity> getByTokenAndUserId(String token, String userId);
    List<TokenRedisEntity> findAllByUserId(String userId);
    void deleteAllByUserId(String userId);
    void deleteByToken(String token);
    void saveToken(TokenRedisEntity tokenRedisEntity);
}