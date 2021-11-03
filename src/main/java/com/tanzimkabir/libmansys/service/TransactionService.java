package com.tanzimkabir.libmansys.service;

import com.tanzimkabir.libmansys.model.*;
import com.tanzimkabir.libmansys.repository.BookRepository;
import com.tanzimkabir.libmansys.repository.TransactionRepository;
import com.tanzimkabir.libmansys.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Handles transactional operations (issue,submit) of Books and Users
 *
 * @author tanzim
 */

@Slf4j
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Value("${user.book.limit}")
    private byte USER_BOOK_LIMIT;

    /**
     * Carries out a transaction to issue a book to a user
     * If any data update is failed, all changes are rolled back
     *
     * @param  transactionRequest - a transactionRequest with necessary data
     * @return true, if successfully executed. Otherwise, false
     */
    @Transactional(rollbackOn = Exception.class)
    public boolean issueBook(TransactionRequest transactionRequest) {
        // Validate if transactionRequest contains all necessary data
        try{
            validateTransactionRequest(transactionRequest);
        } catch (Exception e){
            log.error("Invalid data.");
            throw new IllegalArgumentException("Invalid transaction request");
        }
        // Get the user to whom book will be issued
        User user = userRepository.getByUserName(transactionRequest.getUserName());
        if (user == null) {
            log.info("No User found with username: {}", transactionRequest.getUserName());
            throw new EntityNotFoundException("Could not find user of username " + transactionRequest.getUserName());
        }
        // Check if user's book count will exceed limit or not
        if (user.getBookCount() + transactionRequest.getAmount() > USER_BOOK_LIMIT) {
            log.info("User cannot be issued books. Number of issued books will exceed preset limit: {}", USER_BOOK_LIMIT);
            return false;
        }
        // Get book to be issued
        Book book = bookRepository.getByNameAndAuthor(transactionRequest.getBookName(), transactionRequest.getBookAuthor());
        if (book == null) {
            log.info("No Book found with name: {} and author: {}", transactionRequest.getBookName(), transactionRequest.getBookAuthor());
            throw new EntityNotFoundException("No Book found with Name: " + transactionRequest.getBookName() + " and author: " + transactionRequest.getBookAuthor());
        }
        // Check if there are enough copies of the book to issue
        if (book.getAmount() < transactionRequest.getAmount()) {
            log.info("Book cannot be issued. Not enough books. Required {}, available {}", transactionRequest.getAmount(), book.getAmount());
            return false;
        }
        // Set the issuance as a transaction
        try {
            //See if book was issued to user previously
            Transaction transaction = transactionRepository.getByUserAndBook(user,book);
            if(transaction == null){
                transaction = new Transaction();
                transaction.setUser(user);
                transaction.setBook(book);
            }
            transaction.setUpdatedDate(LocalDateTime.now());
            transaction.setAmount(transaction.getAmount() + transactionRequest.getAmount());
            transactionRepository.save(transaction);
            // Reduce available books
            book.setAmount(book.getAmount() - transactionRequest.getAmount());
            bookRepository.save(book);
            // Add number of books to user
            user.setBookCount(user.getBookCount() + transactionRequest.getAmount());
            userRepository.save(user);
            log.info("Book Id:{} issued to User Id:{}, amount:{}", book.getId(), user.getId(), transactionRequest.getAmount());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Could not issue book to user.");
            return false;
        }
    }

    /**
     * Carries out a transaction to submit a book by user
     * If any data update is failed, all changes are rolled back
     *
     * @param  transactionRequest - a transactionRequest with necessary data
     * @return true, if successfully executed. Otherwise, false
     */
    @Transactional(rollbackOn = Exception.class)
    public boolean submitBook(TransactionRequest transactionRequest) {
        // Validate if transactionRequest contains all necessary data
        try{
            validateTransactionRequest(transactionRequest);
        } catch (Exception e){
            log.error("Invalid data.");
            throw new IllegalArgumentException("Invalid transaction request");
        }
        // Get the user who will submit book
        User user = userRepository.getByUserName(transactionRequest.getUserName());
        if (user == null) {
            log.info("No User found with username: {}", transactionRequest.getUserName());
            throw new EntityNotFoundException("Could not find user of username " + transactionRequest.getUserName());
        }
        // Get book to be submitted
        Book book = bookRepository.getByNameAndAuthor(transactionRequest.getBookName(), transactionRequest.getBookAuthor());
        if (book == null) {
            log.info("No Book found with name: {} and author: {}", transactionRequest.getBookName(), transactionRequest.getBookAuthor());
            throw new EntityNotFoundException("No Book found with Name: " + transactionRequest.getBookName() + " and author: " + transactionRequest.getBookAuthor());
        }
        // Check if user has enough copies of particular book
        if (transactionRequest.getAmount() > transactionRepository.getSumByUserAndBook(user,book)) {
            log.info("User cannot submit books. Number of submitted books exceeds issued: {}", USER_BOOK_LIMIT);
            return false;
        }
        // Set the submitting as a transaction
        try {
            Transaction transaction = transactionRepository.getByUserAndBook(user,book);
            transaction.setAmount(transaction.getAmount() - transactionRequest.getAmount());
            transactionRepository.save(transaction);
            // Increase available books
            book.setAmount(book.getAmount() + transactionRequest.getAmount());
            bookRepository.save(book);
            // Reduce number of books of user
            user.setBookCount(user.getBookCount() - transactionRequest.getAmount());
            userRepository.save(user);
            log.info("Book Id:{} submitted by User Id:{}, amount:{}", book.getId(), user.getId(), transactionRequest.getAmount());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Could not issue book to user.");
            return false;
        }
    }

    /**
     * This method is separately defined only to check the Validity of TransactionRequest
     * parameter. Allows custom handling of a Bad Request.
     *
     * @param  transactionRequest - a transactionRequest to validate
     */
    private void validateTransactionRequest(@Valid TransactionRequest transactionRequest){}
}
