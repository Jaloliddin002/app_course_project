package com.itransition.courseproject.security.jwt;

import com.itransition.courseproject.exception.auth.JwtValidationException;
import com.itransition.courseproject.model.entity.user.User;
import com.itransition.courseproject.model.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.itransition.courseproject.util.constant.AuthConst.*;

@Service
public class JWTokenProvider {
    @Value("${jwt.access.secret.key}")
    private String accessTokenSecretKey;
    @Value("${jwt.access.expiration.time}")
    private long accessTokenExpirationTime;

    public String generateAccessToken(User user) {
        return AUTH_TYPE + generateToken(user);
    }

    public Authentication getAuthentication(String jwtAccessToken) {
        Claims claims = parseToken(jwtAccessToken);
        return new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                getAuthorities(claims.get(ROLE_VALUE, Integer.class))
        );
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim(ROLE_VALUE, user.getRoles())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + (accessTokenExpirationTime)))
                .signWith(SignatureAlgorithm.HS256, accessTokenSecretKey)
                .compact();
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(accessTokenSecretKey)
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException ex) {
            throw new JwtValidationException(TOKEN_EXPIRED);
        } catch (Exception ex) {
            throw new JwtValidationException(INVALID_TOKEN);
        }
    }

    private  Set<GrantedAuthority> getAuthorities(int roleValue) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : Role.values()) {
            if ((roleValue & role.getFlag()) > 0)
                authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

}