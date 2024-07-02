package com.gwan.blog.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = BlogMockSecurityContext.class)
public @interface BlogMockUser {

    String name() default "관맨";

    String email() default "gwan@blog.com";

    String password() default "1234";

}
