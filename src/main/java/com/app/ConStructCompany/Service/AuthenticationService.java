package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Entity.Account;
import com.app.ConStructCompany.Entity.Token;
import com.app.ConStructCompany.Repository.AccountRepository;
import com.app.ConStructCompany.Repository.TokenRepository;
import com.app.ConStructCompany.Request.LoginRequest;
import com.app.ConStructCompany.Request.RegisterRequest;
import com.app.ConStructCompany.Request.dto.LoginDTO;
import com.app.ConStructCompany.Response.LoginResponse;
import com.app.ConStructCompany.Response.RegisterResponse;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final JWTService jwtService;
    private final TokenRepository tokenRepository;
    public RegisterResponse RegisterAccount(RegisterRequest registerRequest){
        var account = accountRepository.findByEmail(registerRequest.getEmail()).orElse(null);
        if (account!=null){
            return RegisterResponse.builder().mess("Email is exist").status(false).build();
        }
        Account acc = Account.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .fullName(registerRequest.getFullName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .isAdmin(registerRequest.isAdmin())
                .createAt(new Date())
                .build();
        System.out.println(acc);
        accountRepository.save(acc);
        return RegisterResponse.builder().mess("Success register account: "+acc.getEmail()+" at time: "+acc.getCreateAt())
                .status(true).build();
    }

    public LoginDTO LoginAccount (LoginRequest loginRequest){
        Account acc = accountRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), acc.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        //generate jwt
        var tokenString = jwtService.generateToken(acc);
        RevokeAccountToken(acc); // thu hoi token tren cac noi khac
        //save account token
        Token saveToken = Token.builder().tokenString(tokenString).account(acc).expiration(false).revoked(false).build();
        tokenRepository.save(saveToken);
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setToken(saveToken.getTokenString());
        loginDTO.setEmail(acc.getEmail());
        loginDTO.setAdmin(acc.isAdmin());
        loginDTO.setFullName(acc.getFullName());
        loginDTO.setId(acc.getId());
        return loginDTO;
    }

    private void RevokeAccountToken(Account account){
        List<Token> tokens = tokenRepository.findAccTokenExist(account.getId());
        if (!tokens.isEmpty() ){
            tokens.forEach(x->{
                x.setExpiration(true);
                x.setRevoked(true);
            });
            tokenRepository.saveAll(tokens);
        }
    }
}
