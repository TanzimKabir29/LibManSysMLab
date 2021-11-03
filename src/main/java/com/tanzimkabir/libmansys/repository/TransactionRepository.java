package com.tanzimkabir.libmansys.repository;

import com.tanzimkabir.libmansys.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query(value = "SELECT sum(issued_amount)-sum(submitted_amount) FROM bookIssuance b WHERE b.userId = :user AND b.book_id = :book", nativeQuery = true)
    public int specificBookCount(@Param(value = "user") Long userId, @Param(value = "book") Long bookId);
}
