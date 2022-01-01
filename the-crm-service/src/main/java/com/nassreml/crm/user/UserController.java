package com.nassreml.crm.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        User userCreated;
        try{
            userCreated = userService.createUser(user);
        }catch (IllegalStateException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }

    public void updateUser(){
        throw new UnsupportedOperationException("This endpoint is not implemented");
    }

    public void deleteUser(){
        throw new UnsupportedOperationException("This endpoint is not implemented");
    }

    public void changeAdminStatus(){
        throw new UnsupportedOperationException("This endpoint is not implemented");
    }

}
