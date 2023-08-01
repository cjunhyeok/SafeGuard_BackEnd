package com.opensw.safeguard.config.login;

import com.opensw.safeguard.filter.login.JwtAuthenticationFilter;
import com.opensw.safeguard.token.JwtTokenProvider;
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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

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
                        })
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        {
                            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                        })
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        {
                            authorizationManagerRequestMatcherRegistry.requestMatchers("/safe/login").permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers("/safe/join/**").permitAll();

                            authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
                        })
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
