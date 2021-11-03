package com.tanzimkabir.libmansys.model;

import lombok.Builder;
import lombok.Data;

/**
 * Meant to carry enough information to convey list of a book's users
 *
 * @author tanzim
 */

@Data
@Builder
public class UserListEntry {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private int copies;
}
