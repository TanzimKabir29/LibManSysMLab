package com.tanzimkabir.libmansys.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    private String userName;
    private String bookName;
    private String bookAuthor;
    private int copies;
}
