package com.nassreml.crm.user;

import org.hibernate.exception.ConstraintViolationException;
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
    public ResponseEntity<List<User>> listUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        final List<String> validationErrors = UserUtils.validateUser(user);
        if (validationErrors.size() > 0) {
            return new ResponseEntity<>(validationErrors, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try {
            userService.createUser(user);
        } catch (IllegalStateException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "{userId}")
    public void updateUser(@PathVariable("userId") Long userId,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String password,
                           @RequestParam(required = false) boolean isAdmin) {
        userService.updateUser(userId, username, password, isAdmin);
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        try {
            userService.deleteUser(userId);
        } catch (IllegalStateException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    public void changeAdminStatus() {
        throw new UnsupportedOperationException("This endpoint is not implemented");
    }

}
