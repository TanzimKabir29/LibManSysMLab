package com.tanzimkabir.libmansys.service;

import com.tanzimkabir.libmansys.model.User;
import com.tanzimkabir.libmansys.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * Handles CRUD operations of User entity
 *
 * @author tanzim
 */

@Slf4j
@Service
public class UserCrudService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a User entity using provided data.
     *
     * @param  user - a User entity that is to be created
     * @return true, if successfully created, otherwise false
     */
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

    /**
     * Retrieves data of a User using provided id.
     *
     * @param  id - id of the User to be retrieved
     * @return retrieved User entity
     */
    public User getUserDetails(Long id) {
        User user = userRepository.getById(id);
        if(user.getId() == null) {
            log.error("Could not find user of Id:{}",id);
            throw new EntityNotFoundException("Could not find user of Id " + id);
        }
        log.info("Found user by Id: {}", user);
        return user;
    }

    /**
     * Retrieves User entity data using provided username.
     *
     * @param  userName - username of the User to be retrieved
     * @return retrieved User entity
     */
    public User getUserDetails(String userName) {
        User user = userRepository.getByUserName(userName);
        if(user == null) {
            log.error("Could not find user of username:{}",userName);
            throw new EntityNotFoundException("Could not find user of userName " + userName);
        }
        log.info("Found user by userName: {}", user);
        return user;
    }

    /**
     * Edits the userName, firstName and lastName of an existing user.
     *
     * @param user - User entity containing id of user to be updated,
     *             along with other data to be overwritten on existing user
     */
    public void updateUserById(User user) {
        try {
            User updatedUser = userRepository.getById(user.getId());
            if(updatedUser.getId() == null) {
                log.error("Could not find user of Id:{}",user.getId());
                throw new EntityNotFoundException("Could not find user of Id " + user.getId());
            }
            updatedUser.setUserName(user.getUserName());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setUpdatedDate(LocalDateTime.now());
            userRepository.save(updatedUser);
            log.info("User data updated.");
        } catch (Exception e) {
            log.error("Book data could not be updated.");
        }
    }

    /**
     * Deletes a user of matching id.
     *
     * @param id - id of the user to be deleted
     * @return true, if user is deleted. Otherwise, false
     */
    public boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            log.info("Deleted user of Id: {}", id);
            return true;
        } catch (EmptyResultDataAccessException noData) {
            log.info("Data with id {} not found.", id);
            throw new EntityNotFoundException("Could not find user of Id " + id);
        } catch (Exception e) {
            log.error("Data could not be deleted.");
            return false;
        }
    }

    /**
     * Deletes a user of matching userName.
     *
     * @param userName - username of the user to be deleted
     * @return true, if user is deleted. Otherwise, false
     */
    public boolean deleteUser(String userName) {
        try {
            User user = getUserDetails(userName);
            userRepository.deleteById(user.getId());
            log.info("Deleted user of userName: {}", userName);
            return true;
        } catch (EmptyResultDataAccessException noData) {
            log.info("Data with username {} not found.", userName);
            throw new EntityNotFoundException("Could not find user of username " + userName);
        } catch (Exception e) {
            log.error("Data could not be deleted.");
            return false;
        }
    }
}