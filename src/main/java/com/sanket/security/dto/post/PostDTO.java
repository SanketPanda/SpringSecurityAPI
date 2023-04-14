package com.sanket.security.dto.post;

import com.sanket.security.dto.comment.CommentDTO;
import com.sanket.security.model.Comments;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PostDTO {

    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    private Long postId;

    @NotBlank
    @Size(max = 500, message = "length should be at max 500 character")
    private String postDescription;

    @NotNull
    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    private Long userId;

    @NotNull
    private boolean isPublicPost = true;

    private List<CommentDTO> commentDTO;
}
