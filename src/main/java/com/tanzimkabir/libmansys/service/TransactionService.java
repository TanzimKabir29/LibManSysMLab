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
import java.util.HashMap;

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

    @Transactional(rollbackOn = Exception.class)
    public boolean issueBook(TransactionRequest transactionRequest) {
        // Get the user to whom book will be issued
        User user = userRepository.getByUserName(transactionRequest.getUserName());
        if (user == null) {
            log.info("No User found with username: {}", transactionRequest.getUserName());
            throw new EntityNotFoundException("Could not find user of username " + transactionRequest.getUserName());
        }
        // Check if user's book count will exceed limit or not
        if (user.getBookCount() + transactionRequest.getCopies() > USER_BOOK_LIMIT) {
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
        if (book.getCopies() < transactionRequest.getCopies()) {
            log.info("Book cannot be issued. Not enough books. Required {}, available {}", transactionRequest.getCopies(), book.getCopies());
            return false;
        }
        // Set the issuance as a transaction
        try {
            Transaction transaction = new Transaction();
            transaction.setUser(user);
            transaction.setBook(book);
            transaction.setType(TrnxType.ISSUE);
            transaction.setIssuedAmount(transactionRequest.getCopies());
            transactionRepository.save(transaction);
            // Reduce available books
            book.setCopies(book.getCopies() - transactionRequest.getCopies());
            HashMap<Long, Integer> newUserList = book.getUserList();
            if(newUserList == null){
                newUserList = new HashMap<>();
            }
            newUserList.put(user.getId(), transactionRequest.getCopies() + newUserList.getOrDefault(user.getId(), 0));
            book.setUserList(newUserList);
            bookRepository.save(book);
            // Add number of books to user
            user.setBookCount(user.getBookCount() + transactionRequest.getCopies());
            HashMap<Long, Integer> newBooksList = user.getBooksList();
            if(newBooksList == null){
                newBooksList = new HashMap<>();
            }
            newBooksList.put(book.getId(), transactionRequest.getCopies() + newBooksList.getOrDefault(book.getId(),0));
            user.setBooksList(newBooksList);
            userRepository.save(user);
            log.info("Book Id:{} issued to User Id:{}, amount:{}", book.getId(), user.getId(), transactionRequest.getCopies());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Could not issue book to user.");
            return false;
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public boolean submitBook(TransactionRequest transactionRequest) {
        // Get the user to whom book will be issued
        User user = userRepository.getByUserName(transactionRequest.getUserName());
        if (user == null) {
            log.info("No User found with username: {}", transactionRequest.getUserName());
            throw new EntityNotFoundException("Could not find user of username " + transactionRequest.getUserName());
        }
        // Get book to be issued
        Book book = bookRepository.getByNameAndAuthor(transactionRequest.getBookName(), transactionRequest.getBookAuthor());
        if (book == null) {
            log.info("No Book found with name: {} and author: {}", transactionRequest.getBookName(), transactionRequest.getBookAuthor());
            throw new EntityNotFoundException("No Book found with Name: " + transactionRequest.getBookName() + " and author: " + transactionRequest.getBookAuthor());
        }
        // Check if user has enough copies of particular book
        HashMap<Long, Integer> newBooksList = user.getBooksList();
        if(newBooksList == null){
            newBooksList = new HashMap<>();
        }
        if (transactionRequest.getCopies() > newBooksList.getOrDefault(book.getId(),0)) {
            log.info("User cannot submit books. Number of submitted books exceeds issued: {}", USER_BOOK_LIMIT);
            return false;
        }
        // Set the submitting as a transaction
        try {
            Transaction transaction = new Transaction();
            transaction.setUser(user);
            transaction.setBook(book);
            transaction.setType(TrnxType.SUBMIT);
            transaction.setSubmittedAmount(transactionRequest.getCopies());
            transactionRepository.save(transaction);
            // Increase available books
            book.setCopies(book.getCopies() + transactionRequest.getCopies());
            HashMap<Long, Integer> newUserList = book.getUserList();
            if(newUserList == null){
                newUserList = new HashMap<>();
            }
            newUserList.put(user.getId(), transactionRequest.getCopies() - newUserList.getOrDefault(user.getId(), 0));
            book.setUserList(newUserList);
            bookRepository.save(book);
            // Reduce number of books of user
            user.setBookCount(user.getBookCount() - transactionRequest.getCopies());
            newBooksList.put(book.getId(), transactionRequest.getCopies() - newBooksList.getOrDefault(book.getId(),0));
            userRepository.save(user);
            log.info("Book Id:{} submitted by User Id:{}, amount:{}", book.getId(), user.getId(), transactionRequest.getCopies());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Could not issue book to user.");
            return false;
        }
    }
}
