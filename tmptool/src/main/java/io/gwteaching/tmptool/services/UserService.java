package io.gwteaching.tmptool.services;

import io.gwteaching.tmptool.dto.User;
import io.gwteaching.tmptool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder cryptPasswordEncoder;

    public User saveUser(User newUser) {
        String password = newUser.getPassword();
        newUser.setPassword(cryptPasswordEncoder.encode(password));
        return userRepository.save(newUser);
    }
}
