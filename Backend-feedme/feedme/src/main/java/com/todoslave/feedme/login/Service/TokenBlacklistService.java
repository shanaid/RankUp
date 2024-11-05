package com.todoslave.feedme.login.Service;

import com.todoslave.feedme.login.TokenBlacklist;
import com.todoslave.feedme.repository.TokenBlacklistRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final TokenBlacklistRepository tokenBlacklistRepository;

    public void addToBlacklist(String token, Date expiryDate) {
        TokenBlacklist tokenBlacklist = new TokenBlacklist(token, expiryDate);
        tokenBlacklistRepository.save(tokenBlacklist);
    }

    public boolean isBlacklisted(String token) {
        Optional<TokenBlacklist> tokenBlacklist = tokenBlacklistRepository.findByToken(token);
        return tokenBlacklist.isPresent();
    }
}