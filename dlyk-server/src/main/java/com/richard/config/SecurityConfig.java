package com.richard.config;


import com.richard.config.filter.TokenVerifyFilter;
import com.richard.config.handler.MyAuthenticationFailureHandler;
import com.richard.config.handler.MyAuthenticationSuccessHandler;
import com.richard.config.handler.MyLogoutSuccessHandler;
import com.richard.constant.Constants;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Resource
    private TokenVerifyFilter tokenVerifyFilter;

    @Resource
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfigurationSource configurationSource) throws Exception {

        System.out.println("securityFilterChain");
        return httpSecurity.
                formLogin((formLogin) -> formLogin.loginProcessingUrl(Constants.LOGIN_URL).
                        usernameParameter("loginAct").
                        passwordParameter("loginPwd").successHandler(myAuthenticationSuccessHandler).failureHandler(myAuthenticationFailureHandler)
                ).
                authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers(Constants.LOGIN_URL).permitAll().anyRequest().authenticated();
                }).
                csrf(AbstractHttpConfigurer::disable).

                // 支持跨域请求
                        cors((cors) -> {
                    cors.configurationSource(configurationSource);
                }).sessionManagement((session) -> {
                    // session 创建策略
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }).

                logout(logout -> {
                    logout.logoutUrl("/api/logout").logoutSuccessHandler(myLogoutSuccessHandler);
                }).
                // 添加自定义的filter
                        addFilterBefore(tokenVerifyFilter, LogoutFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource configurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

