package com.nassreml.crm.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void createUser(final User user) {
        Optional<User> userOptional = userRepository.findUserByUsername(user.getUsername());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("This username is already taken");
        }
        userRepository.save(user);
    }

    public void deleteUser(final Long userId) {
        boolean userExists = userRepository.existsById(userId);
        if (userExists) {
            userRepository.deleteById(userId);
        } else {
            throw new IllegalStateException("The user with id " + userId + " doesn't exists");
        }

    }

    @Transactional
    public void updateUser(Long userId, String username, String password, boolean isAdmin) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalStateException("The user with id " + userId + " doesn't exists");
        }
        if (username != null && username.length() > 0) {
            userOptional.get().setUsername(username);
        }
    }
}
