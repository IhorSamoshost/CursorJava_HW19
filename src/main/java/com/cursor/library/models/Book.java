package com.cursor.library.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Book {

    private String bookId;
    private String name;
    private String description;
    private List<String> authors;
    private int yearOfPublication;
    private int numberOfWords;
    private int rating;

    public Book(String bookId, String name, String description, List<String> authors, int yearOfPublication, int numberOfWords, int rating) {
        this.bookId = bookId;
        this.name = name;
        this.description = description;
        this.authors = authors;
        this.yearOfPublication = yearOfPublication;
        this.numberOfWords = numberOfWords;
        this.rating = rating;
    }

    public Book(String bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (yearOfPublication != book.yearOfPublication) return false;
        if (numberOfWords != book.numberOfWords) return false;
        if (rating != book.rating) return false;
        if (!bookId.equals(book.bookId)) return false;
        if (!name.equals(book.name)) return false;
        if (!description.equals(book.description)) return false;
        return authors.equals(book.authors);
    }

    @Override
    public int hashCode() {
        int result = bookId.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + authors.hashCode();
        result = 31 * result + yearOfPublication;
        result = 31 * result + numberOfWords;
        result = 31 * result + rating;
        return result;
    }
}
