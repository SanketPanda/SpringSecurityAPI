package com.sanket.security.controller.post;
import com.sanket.security.dto.post.PostDTO;
import com.sanket.security.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@CrossOrigin("*")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping("/user/{email}")
    public List<PostDTO> getPostByUser(@PathVariable("email") final String email){
        return  postService.getPostByUser(email);
    }

    @GetMapping("/{postId}")
    public PostDTO getPostById(@PathVariable("postId") final Long postId){
        return  postService.getPostById(postId);
    }

    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody @Valid final PostDTO postDTO){
        final PostDTO newPost = postService.addPost(postDTO);
        return ResponseEntity.status(HttpStatus.OK).body(newPost);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updatePost(@RequestBody @Valid final PostDTO postDTO){
        postService.updatePost(postDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/delete")
    public ResponseEntity<Void> deletePost(@RequestBody @Valid final PostDTO postDTO){
        postService.deletePost(postDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
