package com.tanzimkabir.libmansys.repository;

import com.tanzimkabir.libmansys.model.Book;
import com.tanzimkabir.libmansys.model.Transaction;
import com.tanzimkabir.libmansys.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    Transaction getByUserAndBook(User user, Book book);

    @Query(value = "SELECT SUM(IFNULL(AMOUNT,0)) FROM issued_books b WHERE b.user_id = :user_id AND b.book_id = :book_id", nativeQuery = true)
    int getSumByUserAndBook(@Param("user_id") User user, @Param("book_id") Book book);

    @Query(value = "SELECT book_id, amount FROM issued_books b WHERE b.user_id = :user_id" , nativeQuery = true)
    ArrayList<Object[]> getBooksAndCountByUser(@Param("user_id") User user);

    @Query(value = "SELECT user_id, amount FROM issued_books b WHERE b.book_id = :book_id" , nativeQuery = true)
    ArrayList<Object[]> getUsersAndCountByBook(@Param("book_id") Book book);
}
