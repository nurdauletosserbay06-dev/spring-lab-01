package kz.iitu.springlab.web;

import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/books", produces = "application/json")
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> list(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "10") int limit) {

        if (limit < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "limit must be non-negative"
            );
        }

        return service.findAll(author).stream()
                .limit(limit)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> find(@PathVariable long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book saved = service.create(book);

        return ResponseEntity
                .created(URI.create("/api/books/" + saved.id()))
                .body(saved);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Book> replace(
            @PathVariable long id,
            @RequestBody Book book) {

        return service.replace(id, book)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam String q) {
        return service.findAll(null).stream()
                .filter(book -> book.title()
                        .toLowerCase(java.util.Locale.ROOT)
                        .contains(q.toLowerCase(java.util.Locale.ROOT)))
                .toList();
    }
}