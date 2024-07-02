package com.gwan.blog.config;

import com.gwan.blog.exception.BoardNotFound;
import com.gwan.blog.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@RequiredArgsConstructor
public class BlogPermissionEvaluator implements PermissionEvaluator {

    private final BoardRepository boardRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {

        var userPrincipal = (UserPrincipal) authentication.getPrincipal();

        var board = boardRepository.findById((Long) targetId)
                .orElseThrow(BoardNotFound::new);

        if (!board.getUserId().equals(userPrincipal.getUserId()))
            return false;

        return true;
    }
}
