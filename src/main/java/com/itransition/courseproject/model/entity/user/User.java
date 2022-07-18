package com.itransition.courseproject.model.entity.user;

import com.itransition.courseproject.model.entity.BaseEntity;
import com.itransition.courseproject.model.enums.AuthProvider;
import com.itransition.courseproject.model.enums.Language;
import com.itransition.courseproject.model.enums.Role;
import com.itransition.courseproject.model.enums.Status;
import com.itransition.courseproject.service.user.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "users", indexes = @Index(name = "email_index", columnList = "email"))
public class User extends BaseEntity implements OAuth2User, UserDetails {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private int roles = Role.USER.getFlag();

    @Enumerated(EnumType.STRING)
    private Language language = Language.ENG;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider = AuthProvider.local;

    private String providerId;

    private String imageUrl;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @Transient
    private Map<String, Object> attributes;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return UserService.getRoles(this.getRoles())
                .stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return this.name;
    }

}
