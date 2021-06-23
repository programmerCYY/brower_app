package com.example.backend.service;

public interface TokenService {

    String createToken(String user_id, String role);

    String getUserId(String token);
}
