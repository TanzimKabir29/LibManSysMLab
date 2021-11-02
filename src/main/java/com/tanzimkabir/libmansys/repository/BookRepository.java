package com.tanzimkabir.libmansys.repository;

import com.tanzimkabir.libmansys.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    public ArrayList<Book> getByName(String name);
    public Book getByNameAndAuthor(String name, String author);
    public ArrayList<Book> getByAuthor(String author);
}
