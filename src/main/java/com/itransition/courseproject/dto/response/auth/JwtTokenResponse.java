package com.itransition.courseproject.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class JwtTokenResponse {
    private long id;
    private int roles;
    private String name;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("access_token")
    private String accessToken;
}
