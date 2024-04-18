package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Entity.Token;
import com.app.ConStructCompany.Repository.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    public void saveToken(Token token){
        tokenRepository.save(token);
    }
}
