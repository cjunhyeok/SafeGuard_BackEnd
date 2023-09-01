package com.opensw.safeguard.security.config;

import com.opensw.safeguard.security.entrypoint.RestAuthenticationEntryPoint;
import com.opensw.safeguard.security.filter.JwtAuthenticationFilter;
import com.opensw.safeguard.security.filter.JwtExceptionFilter;
import com.opensw.safeguard.security.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**

 스프링 시큐리티 config

 **/

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtExceptionFilter jwtExceptionFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(httpBasicConfigurer ->
                        {
                            httpBasicConfigurer.disable();
                        })
                .csrf(httpSecurityCsrfConfigurer ->
                        {
                            httpSecurityCsrfConfigurer.disable();
                        }) //
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        {
                            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                        })
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        {
                            authorizationManagerRequestMatcherRegistry.requestMatchers("/api/login").permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers("/api/join/**").permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers("/v3/api-docs", "/swagger*/**").permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers("/api/weather").permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers("/api/upload").permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers("/api/findByUsername/**").permitAll();
                            authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
                        })
                .exceptionHandling(handling ->{
                    handling.authenticationEntryPoint(new RestAuthenticationEntryPoint());
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter,JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
