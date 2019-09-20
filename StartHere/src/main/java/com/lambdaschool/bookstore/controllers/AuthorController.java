package com.lambdaschool.bookstore.controllers;

import com.lambdaschool.bookstore.models.Author;
import com.lambdaschool.bookstore.models.ErrorDetail;
import com.lambdaschool.bookstore.services.AuthorService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AuthorController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    AuthorService authorService;

    // localhost:2019/authors?page=0&size=5&sort=lastname
    @ApiOperation(value = "returns all Authors", response = Author.class, responseContainer = "List")
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
            @ApiResponse(code = 200, message = "Found all authors", response = Author.class),
            @ApiResponse(code = 404, message = "Author not found", response = ErrorDetail.class)
    })
    @GetMapping(value = "/authors", produces = {"application/json"})
    public ResponseEntity<?> findAllAuthors(@PageableDefault(page = 0, size = 5) Pageable pageable, HttpServletRequest request) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed.");

        List<Author> myAuthors = authorService.findAllAuthors(pageable);
        return new ResponseEntity<>(myAuthors, HttpStatus.OK);
    }

    // localhost:2019/authors/{authorid}
    @ApiOperation(value = "return author by id", response = Author.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Found Author by id", response = Author.class),
            @ApiResponse(code = 404, message = "Author NOT found by id", response = ErrorDetail.class)})
    @GetMapping(value = "/authors/{authorid}",
            produces = {"application/json"})
    public ResponseEntity<?> getAuthorById(@PathVariable Long authorid)
    {
        return new ResponseEntity<>(authorService.findAuthorById(authorid), HttpStatus.OK);
    }
}
