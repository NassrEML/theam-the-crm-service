package com.nassreml.crm.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void updateUser(final Long userId, final String username, final String password, final boolean isAdmin) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalStateException("The user with id " + userId + " doesn't exists");
        }

        List<String> validationErrors = UserUtils.validateUsername(username);
        if (username != null) {
            if (validationErrors.size() == 0) {
                userOptional.get().setUsername(username);
            } else {
                throw new IllegalStateException(validationErrors.get(0));
            }
        }

        validationErrors.addAll(UserUtils.validatePassword(password));
        if (password != null) {
            if (validationErrors.size() == 0) {
                userOptional.get().setPassword(password);
            } else {
                throw new IllegalStateException(validationErrors.get(0));
            }
        }
    }

    @Transactional
    public void changeUserStatus(final Long userId, final boolean isAdmin) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalStateException("The user with id " + userId + " doesn't exists");
        }
        if (userOptional.get().isAdmin() != isAdmin) {
            userOptional.get().setAdmin(isAdmin);
        }
    }
}
