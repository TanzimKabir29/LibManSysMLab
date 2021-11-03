package com.tanzimkabir.libmansys.controller;

import com.tanzimkabir.libmansys.model.BookListEntry;
import com.tanzimkabir.libmansys.model.UserListEntry;
import com.tanzimkabir.libmansys.service.ListingService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * This class contains endpoints for listing out Books issued to a specific User, or Users who have been issued a specific Book.
 *
 * @author tanzim
 */

@Slf4j
@RestController
@RequestMapping(value = "/listing")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @GetMapping(value = "/booksbyuserid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookListByUserId(@RequestHeader(value = "request-id") String requestId, @RequestParam(value = "id") Long id){
        MDC.put("request_id", requestId);
        try {
            ArrayList<BookListEntry> books = listingService.getBooksByUserId(id);
            if (!books.isEmpty())
                return ResponseEntity.ok(books);
            else
                return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Could not retrieve book list");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/booksbyusername", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookListByUserName(@RequestHeader(value = "request-id") String requestId, @RequestParam(value = "username") String userName){
        MDC.put("request_id", requestId);
        try {
            ArrayList<BookListEntry> books = listingService.getBooksByUserName(userName);
            if (!books.isEmpty())
                return ResponseEntity.ok(books);
            else
                return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Could not retrieve book list");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/usersbybookid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserListByBookId(@RequestHeader(value = "request-id") String requestId, @RequestParam(value = "id") Long id){
        MDC.put("request_id", requestId);
        try {
            ArrayList<UserListEntry> users = listingService.getUsersByBookId(id);
            if (!users.isEmpty())
                return ResponseEntity.ok(users);
            else
                return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Could not retrieve book list");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/usersbybookname", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserListByBookName(@RequestHeader(value = "request-id") String requestId, @RequestParam(value = "name") String name, @RequestParam(value = "author") String author){
        MDC.put("request_id", requestId);
        try {
            ArrayList<UserListEntry> users = listingService.getUsersByBookName(name,author);
            if (!users.isEmpty())
                return ResponseEntity.ok(users);
            else
                return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Could not retrieve book list");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
