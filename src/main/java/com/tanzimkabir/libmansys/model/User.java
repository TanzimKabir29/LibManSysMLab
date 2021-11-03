package com.tanzimkabir.libmansys.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Entity for users. Users have unique userNames.
 *
 * @author tanzim
 */

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","hibernate_lazy_initializer", "handler", "booksList","books_list"})
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
    @Size(max = 10)
    @NotBlank(message = "Username must be provided")
    private String userName;
    @Size(max = 20)
    @NotBlank(message = "First name must be provided")
    private String firstName;
    @Size(max = 20)
    @NotBlank(message = "Last name must be provided")
    private String lastName;
    private int bookCount = 0;
    // Stores a map of book Id and number of that book issued to this user
    private HashMap<Long, Integer> booksList = null;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss a z", timezone = "GMT+6")
    private LocalDateTime createdDate = LocalDateTime.now();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss a z", timezone = "GMT+6")
    private LocalDateTime updatedDate;
}
