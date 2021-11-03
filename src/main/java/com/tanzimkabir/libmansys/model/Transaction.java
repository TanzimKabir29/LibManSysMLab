package com.tanzimkabir.libmansys.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity handles issuance and return of books. Each transaction has a unique User and Book entity
 * mapped to it, along with the number of books.
 *
 * @author tanzim
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookIssuance")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId")
    private User user;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bookId")
    private Book book;
    private TrnxType type;
    private int issuedAmount = 0;
    private int submittedAmount = 0;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss a z", timezone = "GMT+6")
    private LocalDateTime createdDate = LocalDateTime.now();
}
