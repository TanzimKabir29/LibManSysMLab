package com.tanzimkabir.libmansys.service;

import com.tanzimkabir.libmansys.model.Book;
import com.tanzimkabir.libmansys.model.BookListEntry;
import com.tanzimkabir.libmansys.model.User;
import com.tanzimkabir.libmansys.model.UserListEntry;
import com.tanzimkabir.libmansys.repository.BookRepository;
import com.tanzimkabir.libmansys.repository.TransactionRepository;
import com.tanzimkabir.libmansys.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handles Listing out users who have been issued a book, or Books which are issued to a user
 *
 * @author tanzim
 */

@Slf4j
@Service
public class ListingService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Retrieves the user by id, whose book list is to be retrieved. Meant for code reusability.
     * Calls method {@link #getBookList(User)}.
     *
     * @param id - id of the user whose book list is to be retrieved
     * @return list of books as array of {@link BookListEntry}
     */
    public ArrayList<BookListEntry> getBooksByUserId(Long id) {
        User user = userRepository.getById(id);
        if (user.getId() == null) {
            log.info("User with id: {} not found", id);
            return null;
        }
        return getBookList(user);
    }

    /**
     * Retrieves the user by userName, whose book list is to be retrieved. Meant for code reusability.
     * Calls method {@link #getBookList(User)}.
     *
     * @param userName - userName of the user whose book list is to be retrieved
     * @return list of books as array of {@link BookListEntry}
     */
    public ArrayList<BookListEntry> getBooksByUserName(String userName) {
        User user = userRepository.getByUserName(userName);
        if (user == null) {
            log.info("User with username: {} not found", userName);
            return null;
        }
        return getBookList(user);
    }

    /**
     * Retrieves the list of books and their respective count of a specific user.
     *
     * @param user - user whose book list is to be retrieved
     * @return list of books as array of {@link BookListEntry}
     */
    private ArrayList<BookListEntry> getBookList(User user){
        ArrayList<Object[]> userBooks = transactionRepository.getBooksAndCountByUser(user);
        if (userBooks.isEmpty()){
            log.info("User has no books issued.");
            throw new EntityNotFoundException("User has no books issued.");
        }
        ArrayList<BookListEntry> bookList = new ArrayList<>();
        // Iterating over userBooks to create ArrayList with sufficient data
        for(Object[] i: userBooks){
            Book book = bookRepository.getById(Long.parseLong(i[0].toString()));
            //Using BookListEntry model to pass only necessary data
            bookList.add(
                    BookListEntry.builder()
                            .id(book.getId())
                            .name(book.getName())
                            .author(book.getAuthor())
                            .copies(Integer.parseInt(i[1].toString()))
                            .build()
            );
        }
        bookList.trimToSize();
        if(bookList.isEmpty()){
            log.info("No books found for user.");
            throw new EntityNotFoundException("No books found for user: "+ user);
        }
        log.info("Generated booklist: {}", bookList);
        return bookList;
    }

    /**
     * Retrieves the book by id, whose user list is to be retrieved. Meant for code reusability.
     * Calls method {@link #getUserList(Book)}.
     *
     * @param id - id of the book whose user list is to be retrieved
     * @return list of users as array of {@link UserListEntry}
     */
    public ArrayList<UserListEntry> getUsersByBookId(Long id) {
        Book book = bookRepository.getById(id);
        if (book.getName() == null) {
            log.info("Book with id: {} not found", id);
            return null;
        }
        return getUserList(book);
    }

    /**
     * Retrieves the book by name/title and author, whose user list is to be retrieved.
     * Meant for code reusability. Calls method {@link #getUserList(Book)}.
     *
     * @param name - name/title of the book whose user list is to be retrieved
     * @param author - author of the book whose user list is to be retrieved
     * @return list of users as array of {@link UserListEntry}
     */
    public ArrayList<UserListEntry> getUsersByBookName(String name, String author) {
        Book book = bookRepository.getByNameAndAuthor(name,author);
        if (book == null) {
            log.info("Book with name: {} not found", name);
            return null;
        }
        return getUserList(book);
    }

    /**
     * Retrieves the list of users and their respective count who are issued a specific book.
     *
     * @param book - book whose user list is to be retrieved
     * @return list of users as array of {@link UserListEntry}
     */
    private ArrayList<UserListEntry> getUserList(Book book){
        ArrayList<Object[]> bookUsers = transactionRepository.getUsersAndCountByBook(book);
        if (bookUsers.isEmpty()){
            log.info("Book not issued to any users.");
            throw new EntityNotFoundException("Book not issued to any users.");
        }
        // Iterating over bookUsers to create ArrayList with sufficient data
        ArrayList<UserListEntry> userList = new ArrayList<>();
        for(Object[] i: bookUsers){
            User user = userRepository.getById(Long.parseLong(i[0].toString()));
            //Using UserListEntry model to pass only necessary data
            userList.add(
                    UserListEntry.builder()
                            .id(user.getId())
                            .userName(user.getUserName())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .copies(Integer.parseInt(i[1].toString()))
                            .build()
            );
        }
        userList.trimToSize();
        if(userList.isEmpty()){
            log.info("No users found for book.");
            throw new EntityNotFoundException("No users found for book: "+ book);
        }
        log.info("Generated userList: {}", userList);
        return userList;
    }
}
