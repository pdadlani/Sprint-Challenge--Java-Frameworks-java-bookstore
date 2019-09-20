package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.repository.AuthorRepository;
import com.lambdaschool.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "bookService")
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookrepo;

    @Autowired
    AuthorRepository authorrepo;

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        List<Book> books = new ArrayList<>();
        bookrepo.findAll(pageable).iterator().forEachRemaining(books::add);
        return books;
    }

    @Transactional
    @Override
    public Book updateBookInfo(Book book, long bookid) throws ResourceNotFoundException {
        Book currentBook = bookrepo.findById(bookid)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(bookid)));

        if (book.getBooktitle() != null) {
            currentBook.setBooktitle(book.getBooktitle());
        }
        if (book.getAuthors() != null) {
            currentBook.setAuthors(book.getAuthors());
        }
        if (book.getCopy() != null) {
            currentBook.setCopy(book.getCopy());
        }
        if (book.getISBN() != null) {
            currentBook.setISBN(book.getISBN());
        }

        return bookrepo.save(currentBook);
    }

    @Transactional
    @Override
    public void saveBookAuthor(long bookid, long authorid) {
        Book currentBook = bookrepo.findById(bookid).orElseThrow(() -> new EntityNotFoundException(Long.toString(bookid)));
        currentBook.getAuthors().add(authorrepo.findById(authorid).orElseThrow(() -> new EntityNotFoundException(Long.toString(authorid))));
    }

    @Transactional
    @Override
    public void delete(long bookid) throws ResourceNotFoundException {
        if (bookrepo.findById(bookid).isPresent()) {
//            bookrepo.deleteBookAuthors
            bookrepo.deleteById(bookid);
        } else {
            throw new ResourceNotFoundException(Long.toString(bookid));
        }
    }

    @Override
    public Book findBookById(long id) {
        Book foundBook = bookrepo.findById(id)
                                       .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
        return foundBook;
    }

//    @Override
//    public List<Book> findBookByTitle(String name) {
//        List<Book> list = new ArrayList<>();
//        bookrepo.find
//        return null;
//    }
}
