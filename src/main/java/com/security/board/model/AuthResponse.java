package com.security.board.model;

import lombok.Builder;

@Builder
public record AuthResponse(String token) {}
