package com.tanzimkabir.libmansys.repository;

import com.tanzimkabir.libmansys.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    ArrayList<Book> getByName(String name);
    Book getByNameAndAuthor(String name, String author);
    ArrayList<Book> getByAuthor(String author);
}
