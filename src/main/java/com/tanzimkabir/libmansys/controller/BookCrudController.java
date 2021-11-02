package com.tanzimkabir.libmansys.controller;

import com.tanzimkabir.libmansys.model.Book;
import com.tanzimkabir.libmansys.service.BookCrudService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping(value = "/book")
public class BookCrudController {

    @Autowired
    private BookCrudService bookCrudService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> createBookWithDetails(@RequestHeader(value = "request-id") String requestId, @RequestBody Book book) {
        MDC.put("request_id", requestId);
        if (bookCrudService.createBookWithDetails(book)) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getbyid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookById(@RequestHeader(value = "request-id") String requestId, @RequestParam(value = "id") Long id) {
        MDC.put("request_id", requestId);
        try {
            Book Book = bookCrudService.getBookDetails(id);
            log.info("Retrieved Book : {}", Book);
            if (Book != null)
                return ResponseEntity.ok(Book);
            else
                return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Could not retrieve Book");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getbyname", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookByName(@RequestHeader(value = "request-id") String requestId, @RequestParam(value = "name") String name) {
        MDC.put("request_id", requestId);
        try {
            ArrayList<Book> book = bookCrudService.getBookDetails(name);
            log.info("Retrieved Book : {}", book);
            if (!book.isEmpty())
                return ResponseEntity.ok(book);
            else
                return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Could not retrieve Book");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getbynameandauthor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookByNameAndAuthor(@RequestHeader(value = "request-id") String requestId, @RequestParam(value = "name") String name, @RequestParam(value = "author") String author) {
        MDC.put("request_id", requestId);
        try {
            Book book = bookCrudService.getBookDetails(name, author);
            log.info("Retrieved Book : {}", book);
            if (book != null)
                return ResponseEntity.ok(book);
            else
                return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Could not retrieve Book");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<?> updateBookDetails(@RequestHeader(value = "request-id") String requestId, @RequestBody Book book) {
        MDC.put("request_id", requestId);
        try {
            bookCrudService.updateBookById(book);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Book could not be created.");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deletebyid")
    public ResponseEntity<?> deleteBookById(@RequestHeader(value = "request-id") String requestId, @RequestParam(value = "id") Long id) {
        MDC.put("request_id", requestId);
        if (bookCrudService.deleteBook(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deletebyname")
    public ResponseEntity<?> deleteBookByname(@RequestHeader(value = "request-id") String requestId, @RequestParam(value = "name") String name, @RequestParam(value = "author") String author) {
        MDC.put("request_id", requestId);
        if (bookCrudService.deleteBook(name, author)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}