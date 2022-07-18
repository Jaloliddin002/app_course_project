package com.itransition.courseproject.service.user;


import com.itransition.courseproject.dto.request.auth.RegisterRequest;
import com.itransition.courseproject.dto.request.user.UserRoleUpdateRequest;
import com.itransition.courseproject.dto.request.user.UserStatusRequest;
import com.itransition.courseproject.dto.response.APIResponse;
import com.itransition.courseproject.dto.response.auth.JwtTokenResponse;
import com.itransition.courseproject.dto.response.user.UserResponse;
import com.itransition.courseproject.exception.auth.AuthorizationRequiredException;
import com.itransition.courseproject.exception.user.UserNotFoundException;
import com.itransition.courseproject.model.entity.user.User;
import com.itransition.courseproject.model.enums.Language;
import com.itransition.courseproject.model.enums.Role;
import com.itransition.courseproject.model.enums.Status;
import com.itransition.courseproject.repository.user.UserRepository;
import com.itransition.courseproject.service.CRUDService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService implements CRUDService<Long, RegisterRequest> {


    private final UserRepository repository;
    private final ModelMapper modelMapper;


    @Override
    public APIResponse create(RegisterRequest userRegistrationRequest) {
        User user = repository.save(modelMapper.map(userRegistrationRequest, User.class));
        return APIResponse.success(user);
    }

    @Override
    public APIResponse get(Long id) {
        User user = repository.findById(id).orElseThrow(() -> {
            throw new UserNotFoundException(String.valueOf(id));
        });
        return APIResponse.success(user);
    }

    @Override
    public APIResponse update(Long id, RegisterRequest userUpdateRequest) {
        User user = repository.findById(id).orElseThrow(() -> {
            throw new UserNotFoundException(String.valueOf(id));
        });
        modelMapper.map(userUpdateRequest, user);
        return APIResponse.success(repository.save(user));
    }

    @Override
    public APIResponse delete(Long id) {
        User user = repository.findById(id).orElseThrow(() -> {
            throw new UserNotFoundException(String.valueOf(id));
        });
        repository.delete(user);
        return APIResponse.success(user);
    }

    @Override
    public APIResponse getAll() {
        return null;
    }

    public APIResponse getUser(String email) {
        return APIResponse.success(
                modelMapper.map(repository
                                .findByEmailAndStatusIsNot(email, Status.DELETED)
                                .orElseThrow(() -> new UserNotFoundException(email)),
                        JwtTokenResponse.class));
    }

    public APIResponse getUserList() {
        return APIResponse.success(
                repository
                        .findAll()
                        .stream()
                        .filter(user -> user.getStatus() != Status.DELETED)
                        .map(user -> {
                            return new UserResponse(
                                    user.getId(),
                                    user.getName(),
                                    user.getEmail(),
                                    user.getStatus().name(),
                                    user.getLanguage().name(),
                                    getRoles(user.getRoles()),
                                    user.getImageUrl());
                        }).collect(Collectors.toList()));
    }

    public APIResponse updateUserStatus(UserStatusRequest request, String email) {
        if (repository.existsByEmailAndStatus(email, Status.ACTIVE)) {
            repository.updateUserStatus(Status.valueOf(request.getAction()), request.getContent());
            return APIResponse.success(HttpStatus.OK.value());
        }
        throw new AuthorizationRequiredException();
    }

    public APIResponse updateUserRole(UserRoleUpdateRequest request, String email) {
        if (repository.existsByEmailAndStatus(email, Status.ACTIVE)) {
            repository.updateUserRole(request.isSetAsAdmin()
                    ? (Role.USER.getFlag() + Role.ADMIN.getFlag())
                    : Role.USER.getFlag(), request.getContent());
            return APIResponse.success(HttpStatus.OK.value());
        }
        throw new AuthorizationRequiredException();
    }

    public static List<String> getRoles(int roleValue) {
        List<String> roleList = new ArrayList<>();
        for (Role role : Role.values()) {
            if ((roleValue & role.getFlag()) > 0)
                roleList.add(role.getName());
        }
        return roleList;
    }

    public APIResponse updateLanguage(Long userId, String lang) {
        final User user = repository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException(String.valueOf(userId));
        });
        user.setLanguage(Language.valueOf(lang.toUpperCase()));
        repository.save(user);
        return APIResponse.success(HttpStatus.OK.value());
    }
}
