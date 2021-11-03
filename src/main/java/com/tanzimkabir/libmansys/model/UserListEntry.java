package com.tanzimkabir.libmansys.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserListEntry {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private int copies;
}
