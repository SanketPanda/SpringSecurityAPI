package com.sanket.security.controller.user;

import com.sanket.security.dto.user.UserDTO;
import com.sanket.security.dto.user.UsersDTO;
import com.sanket.security.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UsersDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public UserDTO getByEmail(@PathVariable("email") String email){
        return  userService.getUser(email);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody @Valid UsersDTO userDTO){
        userService.update(userDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Void> delete(@PathVariable("email") String email){
        userService.delete(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
