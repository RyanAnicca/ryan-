package com.example.userdemo.model.repository;

import com.example.userdemo.token.Tokens;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TokenRepository {
    void save(Tokens token);

    void saveAll(List<Tokens> tokens);
    void updateToken(Tokens token);

    void deleteToken(int id);

    Tokens findTokenById(int id);

    List<Tokens> findAllTokens();

    List<Tokens> findAllValidTokenByUser(int id);

    Optional<Tokens> findByToken(String token);
}
