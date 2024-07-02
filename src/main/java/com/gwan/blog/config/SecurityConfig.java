package com.gwan.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwan.blog.config.filter.EmailPasswordAuthFilter;
import com.gwan.blog.config.handler.Http401Handler;
import com.gwan.blog.config.handler.Http403Handler;
import com.gwan.blog.config.handler.LoginFailHandler;
import com.gwan.blog.config.handler.LoginSuccessHandler;
import com.gwan.blog.domain.User;
import com.gwan.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        // 접근 권한 및 접근 가능 경로 설정
        http.authorizeHttpRequests(authorizeRequest ->
                authorizeRequest
                        .anyRequest().permitAll()
        );

        // Security Filter 등록
        http.addFilterBefore(usernamePasswordAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class).rememberMe(rememberMeConfigurer ->
                rememberMeConfigurer
                        .rememberMeParameter("remember")
                        .alwaysRemember(false)
                        .tokenValiditySeconds(2595200)
        );

        // Security Exception Handler
        http.exceptionHandling(e -> {
            e.accessDeniedHandler(new Http403Handler(objectMapper));
            e.authenticationEntryPoint(new Http401Handler());
        });


        return http.build();
    }

    @Bean
    public EmailPasswordAuthFilter usernamePasswordAuthenticationFilter() {
        EmailPasswordAuthFilter filter = new EmailPasswordAuthFilter("/api/auth/login", objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        filter.setAuthenticationFailureHandler(new LoginFailHandler(objectMapper));
        filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());

        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        rememberMeServices.setAlwaysRemember(true);
        rememberMeServices.setValiditySeconds(3600 * 24 * 30);
        filter.setRememberMeServices(rememberMeServices);

        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService(userRepository));
        provider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(provider);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {

        return username -> {

            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));

            return new UserPrincipal(user);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
