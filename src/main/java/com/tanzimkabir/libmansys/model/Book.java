package com.tanzimkabir.libmansys.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Entity for books. Multiple books may have the same name. So name,author pair is taken as unique constraint.
 *
 * @author tanzim
 */

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","hibernate_lazy_initializer", "handler", "userList","user_list"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lib_books",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "author"})
        })
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(max = 50)
    @NotBlank(message = "Book name must be provided")
    private String name;
    @Size(max = 50)
    @NotBlank(message = "Boot author name must be provided")
    private String author;
    private int amount = 0;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss a z", timezone = "GMT+6")
    private LocalDateTime createdDate = LocalDateTime.now();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss a z", timezone = "GMT+6")
    private LocalDateTime updatedDate;
}
