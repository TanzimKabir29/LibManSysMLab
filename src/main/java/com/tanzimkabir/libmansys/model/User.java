package com.tanzimkabir.libmansys.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "booksList"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lib_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"userName"})
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private int bookCount = 0;
    private HashMap<Long, Integer> booksList = null;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss a z", timezone = "GMT+6")
    private LocalDateTime createdDate = LocalDateTime.now();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss a z", timezone = "GMT+6")
    private LocalDateTime updatedDate;
}
