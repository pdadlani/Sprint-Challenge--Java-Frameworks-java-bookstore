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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/data")
public class DataController {

    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    BookService bookService;

    @ApiOperation(value = "updates books info", response = Book.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book successfully updated", response = void.class),
            @ApiResponse(code = 500, message = "Error updating book", response = ErrorDetail.class)
    })
    @PutMapping(value = "/books/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> updateBookInfo(@RequestBody
                                                    Book book,
                                            @PathVariable long id, HttpServletRequest request) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed.");
        bookService.updateBookInfo(book, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "updates books author", response = Book.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book successfully updated with new Author", response = void.class),
            @ApiResponse(code = 500, message = "Error updating book author", response = ErrorDetail.class)
    })
    @PostMapping (value = "/books/{bookid}/authors/{authorid}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> updateBookAuthor(@Valid
                                              @RequestBody
                                                    Book book,
                                            @PathVariable long authorid, @PathVariable long bookid, HttpServletRequest request) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed.");
        bookService.saveBookAuthor(bookid, authorid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "deletes book by id", response = Book.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book successfully deleted by id", response = void.class),
            @ApiResponse(code = 500, message = "Error finding book by id", response = ErrorDetail.class)
    })
    @PostMapping (value = "/books/{bookid}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> deleteBookById(@PathVariable long bookid, HttpServletRequest request) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed.");
        bookService.delete(bookid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
