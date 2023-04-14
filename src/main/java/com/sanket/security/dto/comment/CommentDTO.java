package com.sanket.security.dto.comment;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {

    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    private Long commentId;

    @NotBlank
    @Size(max = 250, message = "length should be at max 250 character")
    private String commentDescription;

    @NotNull
    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    private Long postId;

    @NotNull
    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    private Long userId;
}
