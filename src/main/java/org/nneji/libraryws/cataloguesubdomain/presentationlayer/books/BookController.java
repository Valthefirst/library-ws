package org.nneji.libraryws.cataloguesubdomain.presentationlayer.books;

import org.nneji.libraryws.cataloguesubdomain.businesslayer.books.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<BookResponseModel>> getAllBooks() {
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }

    @GetMapping(value = "{isbn}", produces = "application/json")
    public ResponseEntity<BookResponseModel> getBook(@PathVariable String isbn) {
        return ResponseEntity.ok().body(bookService.getBook(isbn));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<BookResponseModel> addBook(@RequestBody BookRequestModel bookRequestModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookRequestModel));
    }

    @PutMapping(consumes = "application/json", value = "{isbn}", produces = "application/json")
    public ResponseEntity<BookResponseModel> updateBook(@RequestBody BookRequestModel bookRequestModel,
                                                       @PathVariable String isbn) {
        BookResponseModel updatedMovie = bookService.updateBook(bookRequestModel, isbn);
        return ResponseEntity.ok().body(updatedMovie);
    }

    @DeleteMapping("{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable String isbn) {
        bookService.deleteBook(isbn);
        return ResponseEntity.noContent().build();
    }
}
