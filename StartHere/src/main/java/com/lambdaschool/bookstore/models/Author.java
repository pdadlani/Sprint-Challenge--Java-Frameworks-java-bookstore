package com.lambdaschool.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Authors", description = "The Author Entity")
@Entity
@Table(name = "author")
public class Author extends Auditable {
    @ApiModelProperty(name = "authorid", value = "Primary key for Author", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long authorid;

    @Column(nullable = false)
    @ApiModelProperty(name = "lastname", value = "Last name of author", required = true, example = "Austen")
    private String lastname;

    @ApiModelProperty(name = "firstname", value = "First name of author", required = true, example = "Jane")
    private String firstname;

    @ApiModelProperty(name = "authorsbooks", value = "Authors of book", required = true, example = "Author 1, Author 2")
    @ManyToMany(mappedBy = "author")
    @JsonIgnoreProperties("author")
    private List<Book> books = new ArrayList<>();

    public Author() {
    }

    public Author(String lastname, String firstname) {
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public long getAuthorid() {
        return authorid;
    }

    public void setAuthorid(long authorid) {
        this.authorid = authorid;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public List<Book> getBooksAuthors() {
        return books;
    }

    public void setBooksAuthors(List<Book> books) {
        this.books = books;
    }
}
