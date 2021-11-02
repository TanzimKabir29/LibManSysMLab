package com.tanzimkabir.libmansys.service;

import com.tanzimkabir.libmansys.model.User;
import com.tanzimkabir.libmansys.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserCrudService {

    @Autowired
    private UserRepository userRepository;

    public void createUserWithDetails(User user) {
        try {
            userRepository.save(user);
            log.info("New User created!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUserDetails(Long id) {
        User user = userRepository.getById(id);
        log.info("Found user by Id: {}", user);
        return user;
    }

    public User getUserDetails(String userName) {
        User user = userRepository.getByUserName(userName);
        log.info("Found user by userName: {}", user);
        return user;
    }

    public void updateUserById(User user) {
        User updatedUser = userRepository.getById(user.getId());
        updatedUser.setUserName(user.getUserName());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setUpdatedDate(LocalDateTime.now());
        userRepository.save(updatedUser);
        log.info("User data updated.");
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("Deleted user of Id: {}", id);
    }

    public void deleteUser(String userName) {
        User user = getUserDetails(userName);
        userRepository.deleteById(user.getId());
        log.info("Deleted user of userName: {}", userName);
    }
}