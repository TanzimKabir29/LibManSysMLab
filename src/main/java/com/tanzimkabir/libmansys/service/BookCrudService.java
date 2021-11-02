package com.tanzimkabir.libmansys.service;

import com.tanzimkabir.libmansys.model.Book;
import com.tanzimkabir.libmansys.model.User;
import com.tanzimkabir.libmansys.repository.BookRepository;
import com.tanzimkabir.libmansys.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BookCrudService {

    @Autowired
    private BookRepository bookRepository;

    public void createBookWithDetails(Book book) {
        try {
            bookRepository.save(book);
            log.info("New Book created!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Book getBookDetails(Long id) {
        Book book = bookRepository.getById(id);
        log.info("Found Book by Id: {}", book);
        return book;
    }

    public ArrayList<Book> getBookDetails(String name) {
        ArrayList<Book> books = bookRepository.getByName(name);
        log.info("Found Book by name: {}", books);
        return books;
    }

    public Book getBookDetails(String name, String author) {
        Book book = bookRepository.getByNameAndAuthor(name, author);
        log.info("Found Book by name and author: {}", book);
        return book;
    }

    public void updateBookById(Book book) {
        try{
            Book updatedBook = bookRepository.getById(book.getId());
            updatedBook.setName(book.getName());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setCopies(book.getCopies());
            updatedBook.setUpdatedDate(LocalDateTime.now());
            bookRepository.save(updatedBook);
            log.info("Book data updated.");
        } catch (EmptyResultDataAccessException noData){
            log.info("Data with id {} not found.", book.getId());
        } catch (Exception e){
            log.info("Book data could not be updated.");
        }

    }

    public boolean deleteBook(Long id) {
        try {
            bookRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException noData) {
            log.info("Data with id {} not found.", id);
            return false;
        } catch (Exception e) {
            log.info("Data could not be deleted.");
            return false;
        }
    }

    public void deleteBook(String name, String author) {
        try {
            Book book = bookRepository.getByNameAndAuthor(name, author);
            bookRepository.deleteById(book.getId());
            log.info("Deleted user of name,author: {},{}", name, author);
        } catch (EmptyResultDataAccessException noData) {
            log.info("Data with name {} and author {} not found.", name, author);
        } catch (Exception e) {
            log.info("Data could not be deleted.");
        }
    }
}