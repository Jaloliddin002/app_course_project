package com.itransition.courseproject.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itransition.courseproject.dto.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.itransition.courseproject.util.constant.AuthConst.AUTHORIZATION;
import static com.itransition.courseproject.util.constant.AuthConst.AUTH_TYPE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
@RequiredArgsConstructor
public class JWTokenFilter extends OncePerRequestFilter {

    private final JWTokenProvider JWTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromHeader(request);
        if (token != null) {
            try {
                SecurityContextHolder.getContext().setAuthentication(JWTokenProvider.getAuthentication(token));
            } catch (Exception ex) {
                response.setStatus(UNAUTHORIZED.value());
                response
                        .getWriter()
                        .write(objectMapper.writeValueAsString(APIResponse.error(null, ex.getMessage(), UNAUTHORIZED)));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
            String header = request.getHeader(AUTHORIZATION);
        if (header != null && header.startsWith(AUTH_TYPE))
            return header.substring(AUTH_TYPE.length());
        return null;
    }

}
