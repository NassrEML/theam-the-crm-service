package com.nassreml.crm.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User createUser(final User user){
        Optional<User> userOptional = userRepository.findUserByUsername(user.getUsername());
        if(userOptional.isPresent()){
            throw new IllegalStateException("This username is already taken");
        }
        return userRepository.save(user);
    }

}
