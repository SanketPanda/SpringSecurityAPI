package com.sanket.security.dto.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(max = 10, message = "length should be at max 10 character")
    private String password;
}
