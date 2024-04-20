package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Request.LoginRequest;
import com.app.ConStructCompany.Request.RegisterRequest;
import com.app.ConStructCompany.Request.dto.LoginDTO;
import com.app.ConStructCompany.Response.RegisterResponse;
import com.app.ConStructCompany.Service.AuthenticationService;
import com.app.ConStructCompany.Service.LogoutService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;
    @Value("${app.fe.url}")
    private String frontendUrl;
    @PostMapping("/login")
    public ResponseEntity<?> loginInto(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) throws URISyntaxException {
        int timeExpires = loginRequest.isRemember() ? -1 : 24*60*60;
        //get domain
        URI uri = new URI(frontendUrl);
        String domain = uri.getHost();
        if (domain.startsWith("www.")) {
            domain = domain.substring(4);
        }
//        System.out.println(domain);
        try{
            //thuc hien login
            LoginDTO loginDTO = authenticationService.LoginAccount(loginRequest);
            //add cookie
            Cookie cookie =new Cookie("jwt",loginDTO.getToken());
            cookie.setDomain(domain);
            cookie.setHttpOnly(false);
            cookie.setPath("/");
            cookie.setMaxAge(timeExpires);
            httpServletResponse.addCookie(cookie);
            loginDTO.setRemember(loginRequest.isRemember());
//            loginDTO.setToken("");
            return ResponseEntity.ok(loginDTO);
        }catch (UsernameNotFoundException | BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerInto(@RequestBody @Valid RegisterRequest registerRequest){
        RegisterResponse registerResponse = authenticationService.RegisterAccount(registerRequest);
        if (!registerResponse.isStatus()){
            return ResponseEntity.badRequest().body(registerResponse.getMess());
        }
        return ResponseEntity.ok(registerResponse.getMess());
    }
    @PostMapping("/registerbyadmin")
    public ResponseEntity<?> registerAdmin(@RequestBody @Valid RegisterRequest registerRequest , @RequestParam String cod){
        if (cod.equals("0411namvu")){
            RegisterResponse registerResponse = authenticationService.RegisterAccount(registerRequest);
            if (!registerResponse.isStatus()){
                return ResponseEntity.badRequest().body(registerResponse.getMess());
            }
            return ResponseEntity.ok(registerResponse.getMess());
        }
        return ResponseEntity.badRequest().body("nothing");
    }
}
