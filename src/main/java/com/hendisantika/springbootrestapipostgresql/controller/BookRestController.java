package com.hendisantika.springbootrestapipostgresql.controller;

import com.hendisantika.springbootrestapipostgresql.entity.Book;
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

import java.util.Collection;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-rest-api-postgresql
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-01-18
 * Time: 22:07
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/api/books")
public class BookRestController {

    @Autowired
    private BookRepository repository;

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(repository.save(book), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Book>> getAllBooks() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookWithId(@PathVariable Long id) {
        return new ResponseEntity<Book>(repository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<Collection<Book>> findBookWithName(@RequestParam(value = "name") String name) {
        return new ResponseEntity<>(repository.findByName(name), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBookFromDB(@PathVariable("id") long id, @RequestBody Book book) {

        Optional<Book> currentBookOpt = repository.findById(id);
        Book currentBook = currentBookOpt.get();
        currentBook.setName(book.getName());
        currentBook.setDescription(book.getDescription());
        currentBook.setTags(book.getTags());

        return new ResponseEntity<>(repository.save(currentBook), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteBookWithId(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllBooks() {
        repository.deleteAll();
    }
}
//DelteMapping i perket delete ne HTTP dhe sherben per te fshire.
//PostMapping i perket post  ne HTTP dhe sherben per te postuar ose ruajtur.
//GetMapping i perket get ne HTTP dhe sherben per te marre nga backend.
//PutMapping i perket put ne HTTP dhe sherben per te vendosur.
//@RequestMapping("/api/books") eshte prefix ne nje path
// RequestBody per te thene se metoda post ka nje trup
//Path variable qe sherben per Url ne fillim vihet request mapping dhe me pas url.
// autowired sherben per te importuar
//pathh variable sherben per te marre tipin variablit dhe per ta futur funksion