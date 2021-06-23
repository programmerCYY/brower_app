package com.example.backend.service.serviceimpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.backend.enums.ErrorCode;
import com.example.backend.exception.BusinessException;
import com.example.backend.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.token-secret}")
    private String token_secret;

    @Override
    public String createToken(String user_id, String role) {
        String token = "";
        token = JWT.create().withAudience(user_id, role)
                .sign(Algorithm.HMAC256(token_secret));
        return token;
    }

    @Override
    public String getUserId(String token) {
        String user_id = "";
        try {
            user_id = JWT.decode(token).getAudience().get(0);
            return user_id;
        } catch (JWTDecodeException e) {
            throw new BusinessException(ErrorCode.FORBIDDEN_COMMON);
        }
    }
}
