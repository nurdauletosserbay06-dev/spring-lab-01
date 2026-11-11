
package kz.iitu.springlab.catalog;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    // USER and ADMIN can view books
    public List<Book> findAll(String author) {
        if (author == null) {
            return repository.findAll();
        }

        return repository.findAll().stream()
                .filter(book -> book.author()
                        .toLowerCase(Locale.ROOT)
                        .contains(author.toLowerCase(Locale.ROOT)))
                .toList();
    }

    // USER and ADMIN can find books
    public Optional<Book> findById(long id) {
        return repository.findById(id);
    }

    // Only ADMIN can create books
    @PreAuthorize("hasRole('ADMIN')")
    public Book create(Book book) {
        return repository.save(
                new Book(null, book.title(), book.author(), book.year())
        );
    }

    // Only ADMIN can update books
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<Book> replace(long id, Book book) {
        return repository.findById(id)
                .map(existing -> repository.save(
                        new Book(id, book.title(), book.author(), book.year())
                ));
    }

    // Only ADMIN can delete books
    @PreAuthorize("hasRole('ADMIN')")
    public boolean delete(long id) {
        return repository.deleteById(id);
    }
}
