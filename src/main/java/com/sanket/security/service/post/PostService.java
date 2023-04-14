package com.sanket.security.service.post;

import com.sanket.security.dao.PostsRepository;
import com.sanket.security.dto.post.PostDTO;
import com.sanket.security.dto.user.UserDTO;
import com.sanket.security.model.Post;
import com.sanket.security.model.User;
import com.sanket.security.service.post.exception.PostIdRequiredException;
import com.sanket.security.service.post.exception.PostNotFoundException;
import com.sanket.security.service.user.UserService;
import com.sanket.security.service.user.exception.UnAuthorizedAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    UserService userService;

    @Autowired
    PostsRepository postsRepository;

    public List<PostDTO> getPostByUser(final String email){
        final UserDTO userDTO = userService.getUser(email);
        final List<Post> postList = postsRepository.findPostByUserId(userDTO.getUserId());
        return postList.stream().map(PostMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    public PostDTO getPostById(final Long postId){
        final Post post = postsRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        return PostMapper.INSTANCE.toDTO(post);
    }

    @Transactional
    public PostDTO addPost(final PostDTO postDTO){
        final User user = userService.getUser(postDTO.getUserId());
        final String currentAuthenticatedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!currentAuthenticatedUser.equals(user.getEmail())) throw new UnAuthorizedAction("You are not authorized to add post of user: "+user.getEmail());
        final Post post = PostMapper.INSTANCE.toEntity(postDTO);
        post.setUser(user);
        final Post newPost = postsRepository.save(post);
        return PostMapper.INSTANCE.toDTO(newPost);
    }

    @Transactional
    public void updatePost(final PostDTO postDTO){
        final User user = userService.getUser(postDTO.getUserId());
        final String currentAuthenticatedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!currentAuthenticatedUser.equals(user.getEmail())) throw new UnAuthorizedAction("You are not authorized to update post of user: "+user.getEmail());
        Optional.ofNullable(postDTO.getPostId()).orElseThrow(PostIdRequiredException::new);
        final Post post = postsRepository.findById(postDTO.getPostId()).orElseThrow(() -> new PostNotFoundException(postDTO.getPostId()));
        PostMapper.toEntity(postDTO, post);
    }

    @Transactional
    public void deletePost(final PostDTO postDTO){
        final User user = userService.getUser(postDTO.getUserId());
        final String currentAuthenticatedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!currentAuthenticatedUser.equals(user.getEmail())) throw new UnAuthorizedAction("You are not authorized to delete post of user: "+user.getEmail());
        Optional.ofNullable(postDTO.getPostId()).orElseThrow(PostIdRequiredException::new);
        final Post post = postsRepository.findById(postDTO.getPostId()).orElseThrow(() -> new PostNotFoundException(postDTO.getPostId()));
        postsRepository.delete(post);
    }
}
