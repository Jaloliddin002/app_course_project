package com.itransition.courseproject.controller.user;

import com.itransition.courseproject.dto.request.user.UserRoleUpdateRequest;
import com.itransition.courseproject.dto.request.user.UserStatusRequest;
import com.itransition.courseproject.dto.response.APIResponse;
import com.itransition.courseproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.itransition.courseproject.controller.ControllerUtils.USER_URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_URI)
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<APIResponse> getUserList() {
        return ResponseEntity.ok(userService.getUserList());
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<APIResponse> getCurrentUser(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(userService.getUser(email));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/status")
    public ResponseEntity<APIResponse> updateUserStatus(
            @AuthenticationPrincipal String email,
            @RequestBody UserStatusRequest request) {
        return ResponseEntity.ok(userService.updateUserStatus(request, email));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/role")
    public ResponseEntity<APIResponse> updateUserRole(
            @AuthenticationPrincipal String email,
            @RequestBody UserRoleUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUserRole(request, email));
    }

    @GetMapping("/language/{userId}")
    public ResponseEntity<APIResponse> updateLanguage(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(name = "lang", defaultValue = "ENG") String lang
    ){
        return ResponseEntity.ok(userService.updateLanguage(userId,lang));
    }


}
