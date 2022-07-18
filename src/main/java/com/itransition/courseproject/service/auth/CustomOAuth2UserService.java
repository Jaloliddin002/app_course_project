package com.itransition.courseproject.service.auth;

import com.itransition.courseproject.exception.auth.OAuth2AuthenticationProcessingException;
import com.itransition.courseproject.model.entity.user.User;
import com.itransition.courseproject.model.enums.AuthProvider;
import com.itransition.courseproject.model.enums.Status;
import com.itransition.courseproject.repository.user.UserRepository;
import com.itransition.courseproject.security.oauth2.user.OAuth2UserInfo;
import com.itransition.courseproject.security.oauth2.user.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (oAuth2UserInfo.getEmail() == null || oAuth2UserInfo.getEmail().trim().equals("")) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByEmailAndStatusIsNot(oAuth2UserInfo.getEmail(), Status.DELETED);
        User user;
        if (userOptional.isPresent() && userOptional.get().getStatus().equals(Status.ACTIVE)) {
            user = userOptional.get();
            if (!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else if (userOptional.isPresent() && userOptional.get().getStatus().equals(Status.BLOCKED)) {
            throw new OAuth2AuthenticationProcessingException("Your account suspended:(");
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        user.setAttributes(oAuth2User.getAttributes());
        return user;
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User()
                .setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .setProviderId(oAuth2UserInfo.getId())
                .setName(oAuth2UserInfo.getName())
                .setEmail(oAuth2UserInfo.getEmail())
                .setPassword(passwordEncoder.encode(String.valueOf(UUID.randomUUID())))
                .setImageUrl(oAuth2UserInfo.getImageUrl())
                .setRoles(1);
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

}
