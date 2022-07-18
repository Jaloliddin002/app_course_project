package com.itransition.courseproject.util;

import com.itransition.courseproject.model.enums.Role;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AuthenticationUtil {
    public static boolean isAdmin(){
        try {
             return SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getAuthorities()
                    .stream()
                    .anyMatch(role -> role.getAuthority().equals(Role.ADMIN.getName()));
        } catch (Exception ex){
            return false;
        }
    }
}
