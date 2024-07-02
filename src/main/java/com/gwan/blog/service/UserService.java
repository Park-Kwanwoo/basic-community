package com.gwan.blog.service;

import com.gwan.blog.domain.User;
import com.gwan.blog.exception.AlreadyExistEmailException;
import com.gwan.blog.exception.UserNotFound;
import com.gwan.blog.repository.UserRepository;
import com.gwan.blog.request.Signup;
import com.gwan.blog.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signup(Signup signup) {

        Optional<User> findUser = userRepository.findByEmail(signup.getEmail());

        if (findUser.isPresent()) {
            throw new AlreadyExistEmailException();
        }

        // DTO -> Entity 변환해서 던지는것이 좋은가
        User user = signup.toUser();
        userRepository.save(user);
    }

    public UserResponse getUserProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        return new UserResponse(user);
    }
}
