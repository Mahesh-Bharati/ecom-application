package com.app.ecom.controller;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {


    @Autowired
   private UserService userService;
    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable long id)
    {
       return userService.findUserById(id)
               .map(ResponseEntity::ok)
               .orElseGet(() -> ResponseEntity.notFound().build());

    }
    @PutMapping("/api/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable long id ,@RequestBody UserRequest userRequest)
    {
            return new ResponseEntity<>(userService.updateUser(id , userRequest) , HttpStatus.OK);
    }
    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        return new ResponseEntity<>(userService.fetchAllUsers() , HttpStatus.OK);
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest)
    {
         return new ResponseEntity<>(userService.createUser(userRequest),HttpStatus.OK);
    }


}
