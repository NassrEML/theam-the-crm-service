package com.nassreml.crm.user;

import com.nassreml.crm.user.web.request.ChangeAdminRequest;
import com.nassreml.crm.user.web.response.GenericWebResponse;
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
            return getWebResponse(exception, null, HttpStatus.CONFLICT);
        }
        final String message = "User created";
        return getWebResponse(null, message, HttpStatus.CREATED);
    }

    @PutMapping(path = "{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId,
                                        @RequestParam(required = false) String username,
                                        @RequestParam(required = false) String password,
                                        @RequestParam(required = false) boolean isAdmin) {
        try {
            userService.updateUser(userId, username, password, isAdmin);
        } catch (IllegalStateException exception) {
            return getWebResponse(exception, null, HttpStatus.CONFLICT);
        }
        final String message = "User with id " + userId + " updated";
        return getWebResponse(null, message, HttpStatus.OK);
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<GenericWebResponse> deleteUser(@PathVariable("userId") Long userId) {
        try {
            userService.deleteUser(userId);
        } catch (IllegalStateException exception) {
            return getWebResponse(exception, null, HttpStatus.CONFLICT);
        }
        final String message = "User with id " + userId + " deleted";
        return getWebResponse(null, message, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<GenericWebResponse> changeAdminStatus(@RequestBody ChangeAdminRequest request) {
        try {
            userService.changeUserStatus(request.getId(), request.isAdmin());
        } catch (IllegalStateException exception) {
            return getWebResponse(exception, null, HttpStatus.CONFLICT);
        }
        final String message = "User with id " + request.getId() + " updated";

        return getWebResponse(null, message, HttpStatus.OK);
    }

    private ResponseEntity<GenericWebResponse> getWebResponse(final Exception ex,
                                                              final String message,
                                                              final HttpStatus status) {
        if (ex != null) {
            return new ResponseEntity<>(new GenericWebResponse(ex.getMessage()), status);
        }
        return new ResponseEntity<>(new GenericWebResponse(message), status);
    }

}
