package com.hendisantika.springbootrestapipostgresql.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hendisantika.springbootrestapipostgresql.entity.Author;
import com.hendisantika.springbootrestapipostgresql.entity.Book;
import com.hendisantika.springbootrestapipostgresql.repository.AuthorRepository;
import com.hendisantika.springbootrestapipostgresql.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorRestController.class);
    @Autowired
    private AuthorRepository authorRepository;


    // Get all authors
    @GetMapping
    public List<Author> getAllAuthors() {
        logger.info("Getting all authors");
        return authorRepository.findAll();
    }

    // Get author by ID
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author != null) {
            return new ResponseEntity<>(author, HttpStatus.OK);
        } else {
            logger.warn("Author with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new author
    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        logger.info("Creating author: {}", author);
        Author savedAuthor = authorRepository.save(author);
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }

    // Update an existing author
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        logger.info("Updating author with id {}: {}", id, author);
        Author existingAuthor = authorRepository.findById(id).orElse(null);
        if (existingAuthor != null) {
            existingAuthor.setName(author.getName());
            existingAuthor.setBio(author.getBio());
            authorRepository.save(existingAuthor);
            return new ResponseEntity<>(existingAuthor, HttpStatus.OK);
        } else {
            logger.warn("Author with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an author
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        logger.info("Deleting author with id: {}", id);
        Author existingAuthor = authorRepository.findById(id).orElse(null);
        if (existingAuthor != null) {
            authorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.warn("Author with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
