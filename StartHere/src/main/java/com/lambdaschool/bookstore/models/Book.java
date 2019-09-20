package com.lambdaschool.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookid;

    @Column(nullable = false)
    private String booktitle;

    private String ISBN;

    private int copy;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("book")
    private List<BooksAuthors> booksAuthors = new ArrayList<>();

    public Book() {
    }

    public Book(String booktitle, String ISBN, int copy) {
        this.booktitle = booktitle;
        this.ISBN = ISBN;
        this.copy = copy;
    }

    public long getBookid() {
        return bookid;
    }

    public void setBookid(long bookid) {
        this.bookid = bookid;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getCopy() {
        return copy;
    }

    public void setCopy(int copy) {
        this.copy = copy;
    }

    public List<BooksAuthors> getBooksAuthors() {
        return booksAuthors;
    }

    public void setBooksAuthors(List<BooksAuthors> booksAuthors) {
        this.booksAuthors = booksAuthors;
    }
}
