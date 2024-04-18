package com.app.ConStructCompany.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JWTService {
    private String secretKey = "1234567890ABCDEFGHIKLMNOJKLASJDHSAKJHDKASKAJSASDASDVXCASDAASDASDASD123123123";

    private byte[] getSecretKeyBytes(){
        return secretKey.getBytes();
    }
    private Key getKey(){
        byte[]bytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
    public String generateToken(UserDetails acc){
        return Jwts
                .builder()
                .setSubject(acc.getUsername())
                .signWith(SignatureAlgorithm.HS256,getSecretKeyBytes())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+24*60*60*1000))
                .compact();
    }
    public String extraName(String token){
        Claims claim =extraClaim(token);
        String accMail =claim.getSubject();
        return accMail;
    }
    public Claims extraClaim(String token){
        Claims claims =Jwts.parser().setSigningKey(getSecretKeyBytes()).parseClaimsJws(token).getBody();
        return claims;
    }
    public boolean tokenValid(String token){
        return !checkExpiration(token);
    }
    public boolean checkExpiration(String token){
        Claims claims =extraClaim(token);
        Date expiration =claims.getExpiration();
        return expiration.before(new Date());
    }
}