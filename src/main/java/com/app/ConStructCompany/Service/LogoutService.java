package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Entity.Token;
import com.app.ConStructCompany.Repository.TokenRepository;
import com.app.ConStructCompany.Response.MessageResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        MessageResponse messageResponse = new MessageResponse();
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        if (cookies==null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            messageResponse.setMessage(response,"not found cookie");
            return;
        }
        cookie = Arrays.stream(cookies).filter(x->x.getName().equals("jwt")).findFirst().orElse(null);
        if (cookie==null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            messageResponse.setMessage(response,"Not found cookie");
            return;
        }
        Optional<Token> token = tokenRepository.findTokenByTokenString(cookie.getValue());
        if (token.isEmpty()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            messageResponse.setMessage(response,"not found token");
            return;
        }
        Token delToken = token.get();
        delToken.setExpiration(true);
        delToken.setRevoked(true);
        tokenRepository.save(delToken);
        SecurityContextHolder.clearContext();
        cookie=new Cookie("jwt",null);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
        messageResponse.setMessage(response,"logout success");
    }
}
