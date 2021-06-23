package com.example.backend.controller;



import com.example.backend.service.TokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class BaseController {
    @Resource
    private TokenService tokenService;

    public String getUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return tokenService.getUserId(token);
    }
}
