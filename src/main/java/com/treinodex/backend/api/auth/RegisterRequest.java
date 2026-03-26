package com.treinodex.backend.api.auth;

import com.treinodex.backend.domain.user.UserRole;

public record RegisterRequest(
        String name,
        String email,
        String password,
        UserRole role
) {
}
