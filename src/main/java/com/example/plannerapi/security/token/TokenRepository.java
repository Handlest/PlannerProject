package com.example.plannerapi.security.token;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<TokenRedis, String> {

    Optional<TokenRedis> getByToken(String token);
    Optional<TokenRedis> getByTokenAndUserId(String token, String userId);
    List<TokenRedis> findAllByUserId(String userId);
    void deleteAllByUserId(String userId);
    void deleteByToken(String token);
    void saveToken(TokenRedis tokenRedis);
}