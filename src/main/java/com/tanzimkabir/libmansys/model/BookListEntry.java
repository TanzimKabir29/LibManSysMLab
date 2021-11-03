package com.tanzimkabir.libmansys.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookListEntry {
    private Long id;
    private String name;
    private String author;
    private int copies;
}
