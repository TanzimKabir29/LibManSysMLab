package com.tanzimkabir.libmansys.service;

import com.tanzimkabir.libmansys.model.User;
import com.tanzimkabir.libmansys.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserCrudService {

    @Autowired
    private UserRepository userRepository;

    public boolean createUserWithDetails(User user) {
        try {
            userRepository.save(user);
            log.info("New User created!");
            return true;
        } catch (Exception e) {
            log.error("User could not be created with data {}", user);
            return false;
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
        try {
            User updatedUser = userRepository.getById(user.getId());
            updatedUser.setUserName(user.getUserName());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setUpdatedDate(LocalDateTime.now());
            userRepository.save(updatedUser);
            log.info("User data updated.");
        } catch (EmptyResultDataAccessException noData) {
            log.info("Data with id {} not found.", user.getId());
        } catch (Exception e) {
            log.error("Book data could not be updated.");
        }
    }

    public boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            log.info("Deleted user of Id: {}", id);
            return true;
        } catch (EmptyResultDataAccessException noData) {
            log.info("Data with id {} not found.", id);
            return false;
        } catch (Exception e) {
            log.error("Data could not be deleted.");
            return false;
        }
    }

    public boolean deleteUser(String userName) {
        try {
            User user = getUserDetails(userName);
            userRepository.deleteById(user.getId());
            log.info("Deleted user of userName: {}", userName);
            return true;
        } catch (EmptyResultDataAccessException noData) {
            log.info("Data with username {} not found.", userName);
            return false;
        } catch (Exception e) {
            log.error("Data could not be deleted.");
            return false;
        }
    }
}