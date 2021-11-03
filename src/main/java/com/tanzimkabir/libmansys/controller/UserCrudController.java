package com.tanzimkabir.libmansys.controller;

import com.tanzimkabir.libmansys.model.User;
import com.tanzimkabir.libmansys.service.UserCrudService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

/**
 * This class contains endpoints for CRUD operations on User entities.
 *
 * @author tanzim
 */

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserCrudController {

    @Autowired
    private UserCrudService userCrudService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUserWithDetails(@RequestHeader(value = "request-id") String requestId, @Valid  @RequestBody User user) {
        MDC.put("request_id", requestId);
        if (userCrudService.createUserWithDetails(user)) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getbyid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@RequestHeader(value = "request-id") String requestId, @RequestParam(value = "id") Long id) {
        MDC.put("request_id", requestId);
        try {
            User user = userCrudService.getUserDetails(id);
            log.info("Retrieved user : {}", user);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException enf){
            throw new EntityNotFoundException(enf.getMessage());
        } catch (IllegalArgumentException arg){
            throw new EntityNotFoundException(arg.getMessage());
        } catch (Exception e) {
            log.error("Could not retrieve user");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getbyusername", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByUserName(@RequestHeader(value = "request-id") String requestId, @RequestParam(value = "username") String userName) {
        MDC.put("request_id", requestId);
        try {
            User user = userCrudService.getUserDetails(userName);
            log.info("Retrieved user : {}", user);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException enf){
            throw new EntityNotFoundException(enf.getMessage());
        } catch (IllegalArgumentException arg){
            throw new EntityNotFoundException(arg.getMessage());
        } catch (Exception e) {
            log.error("Could not retrieve user");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<?> updateUserDetails(@RequestHeader(value = "request-id") String requestId, @Valid @RequestBody User user) {
        MDC.put("request_id", requestId);
        try {
            userCrudService.updateUserById(user);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (EntityNotFoundException enf){
            throw new EntityNotFoundException(enf.getMessage());
        } catch (IllegalArgumentException arg){
            throw new EntityNotFoundException(arg.getMessage());
        } catch (Exception e) {
            log.error("User could not be created.");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deletebyid")
    public ResponseEntity<?> deleteUserById(@RequestHeader(value = "request-id") String requestId, @RequestParam(value = "id") Long id) {
        MDC.put("request_id", requestId);
        try {
            userCrudService.deleteUser(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (EntityNotFoundException enf){
            throw new EntityNotFoundException(enf.getMessage());
        } catch (IllegalArgumentException arg){
            throw new EntityNotFoundException(arg.getMessage());
        } catch (Exception e) {
            log.error("User could not be deleted.");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deletebyusername")
    public ResponseEntity<?> deleteUserByUserName(@RequestHeader(value = "request-id") String requestId, @RequestParam(value = "username") String userName) {
        MDC.put("request_id", requestId);
        try {
            userCrudService.deleteUser(userName);
            return new ResponseEntity(HttpStatus.OK);
        } catch (EntityNotFoundException enf){
            throw new EntityNotFoundException(enf.getMessage());
        } catch (IllegalArgumentException arg){
            throw new EntityNotFoundException(arg.getMessage());
        } catch (Exception e) {
            log.error("User could not be deleted.");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
