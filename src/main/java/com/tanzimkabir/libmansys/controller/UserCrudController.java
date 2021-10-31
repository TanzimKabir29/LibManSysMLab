package com.tanzimkabir.libmansys.controller;

import com.tanzimkabir.libmansys.model.User;
import com.tanzimkabir.libmansys.service.UserCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserCrudController {

    @Autowired
    private UserCrudService userCrudService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUserWithDetails(@RequestBody User user) {
        try{
            userCrudService.createUserWithDetails(user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            log.error("User could not be created.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getById")
    public ResponseEntity<User> getUserById(@RequestParam(value="id") Long id){
        try{
            User user = userCrudService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e){
            log.error("Could not retrieve user");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getByUserName")
    public ResponseEntity<User> getUserByUserName(@RequestParam(value="userName") String userName){
        try{
            User user = userCrudService.getUserByUserName(userName);
            return ResponseEntity.ok(user);
        } catch (Exception e){
            log.error("Could not retrieve user");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<User> updateUserDetails(@RequestBody User user){
        try{
            userCrudService.updateUserById(user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            log.error("User could not be created.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUserById(@RequestParam(value="id") Long id) {
        try{
            userCrudService.deleteUserById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            log.error("User could not be created.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
