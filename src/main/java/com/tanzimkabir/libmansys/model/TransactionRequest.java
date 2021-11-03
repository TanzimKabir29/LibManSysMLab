package com.tanzimkabir.libmansys.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Handles incoming transaction (book issue, book submit) requests. Is not an Entity, rather a helping class
 * for handling transaction requests.
 *
 * @author tanzim
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    @Size(max = 10)
    @NotBlank(message = "UserName is mandatory")
    private String userName;
    @Size(max = 50)
    @NotBlank(message = "Book name is mandatory")
    private String bookName;
    @Size(max = 50)
    @NotBlank(message = "Book author name is mandatory")
    private String bookAuthor;
    private int copies = 1;
}
