package com.tanzimkabir.libmansys.model;

import lombok.Builder;
import lombok.Data;

/**
 * Meant to carry enough information to convey list of a user's books
 *
 * @author tanzim
 */

@Data
@Builder
public class BookListEntry {
    private Long id;
    private String name;
    private String author;
    private int copies;
}
