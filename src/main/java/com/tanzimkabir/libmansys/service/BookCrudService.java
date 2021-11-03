package com.tanzimkabir.libmansys.service;

import com.tanzimkabir.libmansys.model.Book;
import com.tanzimkabir.libmansys.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Slf4j
@Service
public class BookCrudService {

    @Autowired
    private BookRepository bookRepository;

    public boolean createBookWithDetails(Book book) {
        try {
            Book newBook = bookRepository.getByNameAndAuthor(book.getName(), book.getAuthor());
            if(newBook == null){
                bookRepository.save(book);
                log.info("New Book created!");
            }
            else{
                newBook.setCopies(newBook.getCopies() + book.getCopies());
                bookRepository.save(newBook);
                log.info("Copies added to existing book.");
            }
            return true;
        } catch (Exception e) {
            log.error("Book could not be created with data {}", book);
            return false;
        }
    }

    public Book getBookDetails(Long id) {
        Book book = bookRepository.getById(id);
        if(book == null) {
            log.error("Could not find book of Id:{}",id);
            throw new EntityNotFoundException("Could not find book of Id " + id);
        }
        log.info("Found Book by Id: {}", book);
        return book;
    }

    public ArrayList<Book> getBookDetailsbyName(String name) {
        ArrayList<Book> books = bookRepository.getByName(name);
        if(books.isEmpty()) {
            log.error("Could not find books of name:{}",name);
            throw new EntityNotFoundException("Could not find books of name " + name);
        }
        log.info("Found Book by name: {}", books);
        return books;
    }

    public ArrayList<Book> getBookDetailsbyAuthor(String author) {
        ArrayList<Book> books = bookRepository.getByAuthor(author);
        if(books.isEmpty()) {
            log.error("Could not find books of author:{}",author);
            throw new EntityNotFoundException("Could not find books of author " + author);
        }
        log.info("Found Book by name: {}", books);
        return books;
    }

    public Book getBookDetails(String name, String author) {
        Book book = bookRepository.getByNameAndAuthor(name, author);
        if(book == null) {
            log.error("Could not find book of name:{} and author:{}", name,author);
            throw new EntityNotFoundException("Could not find book of Name " + name + " and Author " + author);
        }
        log.info("Found Book by name and author: {}", book);
        return book;
    }

    public void updateBookById(Book book) {
        try{
            Book updatedBook = bookRepository.getById(book.getId());
            if(updatedBook == null) {
                log.error("Could not find book of Id:{}",book.getId());
                throw new EntityNotFoundException("Could not find book of Id " + book.getId());
            }
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
            throw new EntityNotFoundException("Could not find book of Id " + id);
        } catch (Exception e) {
            log.info("Data could not be deleted.");
            return false;
        }
    }

    public boolean deleteBook(String name, String author) {
        try {
            Book book = bookRepository.getByNameAndAuthor(name, author);
            bookRepository.deleteById(book.getId());
            log.info("Deleted user of name,author: {},{}", name, author);
            return true;
        } catch (EmptyResultDataAccessException noData) {
            log.info("Data with name {} and author {} not found.", name, author);
            throw new EntityNotFoundException("Could not find book of Name " + name + " and Author " + author);
        } catch (Exception e) {
            log.error("Data could not be deleted.");
            return false;
        }
    }
}