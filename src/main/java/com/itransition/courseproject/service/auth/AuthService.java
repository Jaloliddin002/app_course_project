package com.itransition.courseproject.service.auth;

import com.itransition.courseproject.dto.request.auth.LoginRequest;
import com.itransition.courseproject.dto.request.auth.RegisterRequest;
import com.itransition.courseproject.dto.response.APIResponse;
import com.itransition.courseproject.dto.response.auth.JwtTokenResponse;
import com.itransition.courseproject.exception.auth.JwtValidationException;
import com.itransition.courseproject.model.entity.user.User;
import com.itransition.courseproject.model.enums.Status;
import com.itransition.courseproject.repository.user.UserRepository;
import com.itransition.courseproject.security.jwt.JWTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    String LOGIN_ERROR = "Email or Password is incorrect";
    String REGISTRATION_ERROR = "This email already signed up. Please log in!";

    private final UserRepository userRepository;
    private final JWTokenProvider jwTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public APIResponse login(LoginRequest requestDto) {
        Optional<User> optionalUser = userRepository.findByEmailAndStatus(requestDto.getEmail(), Status.ACTIVE);
        if (optionalUser.isPresent() &&
                passwordEncoder.matches(requestDto.getPassword(), optionalUser.get().getPassword())) {
            return APIResponse.success(sendJwtSuccessResponse(optionalUser.get()));
        }
        throw new JwtValidationException(LOGIN_ERROR);
    }

    public APIResponse register(RegisterRequest requestDto) {
        Optional<User> optionalUser = userRepository.findByEmailAndStatusIsNot(requestDto.getEmail(), Status.DELETED);
        if (optionalUser.isEmpty() || optionalUser.get().getStatus().equals(Status.DELETED)) {
            User user;
            user = userRepository.save(
                    new User(
                            requestDto.getName(),
                            requestDto.getEmail(),
                            passwordEncoder.encode(requestDto.getPassword())));
            return APIResponse.success(sendJwtSuccessResponse(user));
        }
        throw new JwtValidationException(REGISTRATION_ERROR);
    }


    private JwtTokenResponse sendJwtSuccessResponse(User user) {
        return new JwtTokenResponse(
                user.getId(),
                user.getRoles(),
                user.getName(),
                user.getImageUrl(),
                jwTokenProvider.generateAccessToken(user));
    }
}
