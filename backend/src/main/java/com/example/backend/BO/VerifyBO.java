package com.example.backend.BO;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VerifyBO {
    @NotBlank(message = "code不能为空")
    private String code;
    @NotBlank(message = "verification不能为空")
    private String verification;
}
