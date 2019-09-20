package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.models.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<Book> findAllBooks(Pageable pageable);

    Book updateBookInfo(Book book, long bookid);

    void saveBookAuthor(long bookid, long authorid);

    void delete(long bookid);

    Book findBookById(long id);

//    List<Book> findBookByTitle(String name);
}
