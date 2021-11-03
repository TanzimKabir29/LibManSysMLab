package com.tanzimkabir.libmansys.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    @NotBlank(message = "UserName is mandatory")
    private String userName;
    @NotBlank(message = "Book name is mandatory")
    private String bookName;
    @NotBlank(message = "Book author name is mandatory")
    private String bookAuthor;
    private int copies = 1;
}
