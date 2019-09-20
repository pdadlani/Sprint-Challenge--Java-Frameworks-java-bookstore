package com.lambdaschool.bookstore.controllers;

import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.models.ErrorDetail;
import com.lambdaschool.bookstore.services.BookService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService bookService;

    // localhost:2019/books?page=0&size=5&sort=booktitle
    @ApiOperation(value = "returns all Books", response = Book.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found all books", response = Book.class),
            @ApiResponse(code = 404, message = "Book not found", response = ErrorDetail.class)
    })
    @GetMapping(value = "/books", produces = {"application/json"})
    public ResponseEntity<?> findAllBooks(@PageableDefault(size = 5)
                                                    Pageable pageable, HttpServletRequest request) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed.");
        List<Book> myBooks = bookService.findAllBooks(pageable);
        return new ResponseEntity<>(myBooks, HttpStatus.OK);
    }

    //GET localhost:2019/books/{bookid}
    @ApiOperation(value = "Find book by id", response = Book.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Found book by ID", response = Book.class),
            @ApiResponse(code = 404, message = "Book NOT found by ID", response = ErrorDetail.class)})
    @GetMapping(value = "/books/{bookid}",
            produces = {"application/json"})
    public ResponseEntity<?> getBookById(@PathVariable Long bookid)
    {
        Book b = bookService.findBookById(bookid);
        return new ResponseEntity<>(b, HttpStatus.OK);
    }

}
