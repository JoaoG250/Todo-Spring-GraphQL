package com.github.joaog250.todospringgraphql.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthTokensDto {
    private String accessToken;
    private String refreshToken;
}
