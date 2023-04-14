package com.sanket.security.dto.user;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UsersDTO {

    @NotNull
    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    private Long userId;

    @NotBlank
    @Size(max = 10, message = "length should be at max 10 character")
    private String firstName;

    @NotBlank
    @Size(max = 10, message = "length should be at max 10 character")
    private String lastName;
}
