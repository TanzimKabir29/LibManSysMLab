package com.tanzimkabir.libmansys.service;

import com.tanzimkabir.libmansys.model.Book;
import com.tanzimkabir.libmansys.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Handles CRUD operations of Book entity
 *
 * @author tanzim
 */

@Slf4j
@Service
public class BookCrudService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Creates a Book entity using provided data.
     *
     * @param  book - a book entity that is to be created
     * @return true, if successfully created, otherwise false
     */
    public boolean createBookWithDetails(Book book) {
        try {
            // Check if such book already exists
            Book newBook = bookRepository.getByNameAndAuthor(book.getName(), book.getAuthor());
            if(newBook == null){
                // Create new book if it doesn't exist
                bookRepository.save(book);
                log.info("New Book created!");
            }
            else{
                // Add copies to the existing book since it exists
                newBook.setAmount(newBook.getAmount() + book.getAmount());
                bookRepository.save(newBook);
                log.info("Copies added to existing book.");
            }
            return true;
        } catch (Exception e) {
            log.error("Book could not be created with data {}", book);
            return false;
        }
    }

    /**
     * Retrieves data of a Book using provided id.
     *
     * @param  id - id of the book to be retrieved
     * @return retrieved Book entity
     */
    public Book getBookDetails(Long id) {
        Book book = bookRepository.getById(id);
        if(book.getName() == null) {
            log.error("Could not find book of Id:{}",id);
            throw new EntityNotFoundException("Could not find book of Id " + id);
        }
        log.info("Found Book by Id: {}", book);
        return book;
    }

    /**
     * Retrieves Book entity data using provided book name.
     *
     * @param  name - name/title of the book to be retrieved
     * @return list of Book entities with matching name
     */
    public ArrayList<Book> getBookDetailsbyName(String name) {
        ArrayList<Book> books = bookRepository.getByName(name);
        if(books.isEmpty()) {
            log.error("Could not find books of name:{}",name);
            throw new EntityNotFoundException("Could not find books of name " + name);
        }
        log.info("Found Book by name: {}", books);
        return books;
    }

    /**
     * Retrieves Book entity data using provided book author.
     *
     * @param  author - author of the book to be retrieved
     * @return list of Book entities with matching author
     */
    public ArrayList<Book> getBookDetailsbyAuthor(String author) {
        ArrayList<Book> books = bookRepository.getByAuthor(author);
        if(books.isEmpty()) {
            log.error("Could not find books of author:{}",author);
            throw new EntityNotFoundException("Could not find books of author " + author);
        }
        log.info("Found Book by name: {}", books);
        return books;
    }

    /**
     * Retrieves Book entity data using provided book name and author.
     *
     * @param name - name/title of the book to be retrieved
     * @param author - author of the book to be retrieved
     * @return specific Book with matching name/title and author
     */
    public Book getBookDetails(String name, String author) {
        Book book = bookRepository.getByNameAndAuthor(name, author);
        if(book == null) {
            log.error("Could not find book of name:{} and author:{}", name,author);
            throw new EntityNotFoundException("Could not find book of Name " + name + " and Author " + author);
        }
        log.info("Found Book by name and author: {}", book);
        return book;
    }

    /**
     * Edits the name/title, author of an existing book.
     *
     * @param book - book entity containing id of book to be updated,
     *             along with other data to be overwritten on existing book
     */
    public void updateBookById(Book book) {
        try{
            Book updatedBook = bookRepository.getById(book.getId());
            if(updatedBook.getName() == null) {
                log.error("Could not find book of Id:{}",book.getId());
                throw new EntityNotFoundException("Could not find book of Id " + book.getId());
            }
            updatedBook.setName(book.getName());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setAmount(book.getAmount());
            updatedBook.setUpdatedDate(LocalDateTime.now());
            bookRepository.save(updatedBook);
            log.info("Book data updated.");
        } catch (EntityNotFoundException noData){
            log.info("Data with id {} not found.", book.getId());
            throw new EntityNotFoundException(noData.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.info("Book data could not be updated.");
        }
    }

    /**
     * Deletes a book of matching id.
     *
     * @param id - id of the book to be deleted
     * @return true, if book is deleted. Otherwise, false
     */
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

    /**
     * Deletes a book of matching name/title and author.
     *
     * @param name - name/title of the book to be deleted
     * @param author - author of the book to be deleted
     * @return true, if book is deleted. Otherwise, false
     */
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