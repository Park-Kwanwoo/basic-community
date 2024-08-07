package com.gwan.blog.config;

import com.gwan.blog.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class MethodSecurityConfig {

    private final BoardRepository boardRepository;

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {

        var handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(new BlogPermissionEvaluator(boardRepository));
        return handler;
    }
}
