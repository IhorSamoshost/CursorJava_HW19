package com.cursor.library.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Used for book creation in some particular method.
 */
@Data
@NoArgsConstructor
public class CreateBookDto {

    private String name;
    private String description;
    private List<String> authors;
    private int yearOfPublication;
    private int numberOfWords;
    private int rating;
}
