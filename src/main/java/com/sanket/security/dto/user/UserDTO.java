package com.sanket.security.dto.user;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserDTO {

    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    private Long userId;

    @NotBlank
    @Size(max = 10, message = "length should be at max 10 character")
    private String firstName;

    @NotBlank
    @Size(max = 10, message = "length should be at max 10 character")
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(max = 10, message = "length should be at max 10 character")
    private String password;

    @NotBlank
    @Size(max = 10, message = "length should be at max 10 character")
    private String roles;
}
