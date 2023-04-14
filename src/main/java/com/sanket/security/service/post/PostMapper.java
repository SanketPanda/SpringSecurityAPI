package com.sanket.security.service.post;

import com.sanket.security.dto.post.PostDTO;
import com.sanket.security.dto.user.UserDTO;
import com.sanket.security.dto.user.UsersDTO;
import com.sanket.security.model.Post;
import com.sanket.security.model.User;
import com.sanket.security.service.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDTO toDTO(Post post);

    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "commentDTO", ignore = true)
    Post toEntity(PostDTO postDTO);

    static void toEntity(final PostDTO postDTO, final Post post){
        post.setPublicPost(postDTO.isPublicPost());
        post.setPostDescription(post.getPostDescription());
    }
}
