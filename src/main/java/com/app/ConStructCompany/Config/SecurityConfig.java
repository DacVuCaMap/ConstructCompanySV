package com.app.ConStructCompany.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    @Bean
    public SecurityFilterChain filterChai(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("api/auth/login","api/auth/registerbyadmin").permitAll()
                                .requestMatchers("api/auth/register","api/auth/account").hasRole("ADMIN")
                                .anyRequest().authenticated()
                ).authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf().disable();  
        http.cors((cors)->cors.configurationSource(corsConfiguration()));
        http.logout((logout)->logout
                .logoutUrl("/api/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
        );
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfiguration(){
        CorsConfiguration corsConfiguration =new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true); // Cho phép gửi cookie
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000","https://tiendong.vercel.app"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }
}
