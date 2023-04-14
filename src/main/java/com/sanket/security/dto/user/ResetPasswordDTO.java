package com.sanket.security.dto.user;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ResetPasswordDTO {
    @NotBlank
    @Size(max = 10, message = "length should be at max 10 character")
    private String oldPassword;

    @NotBlank
    @Size(max = 10, message = "length should be at max 10 character")
    private String newPassword;
}
