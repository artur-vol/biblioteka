package org.example.service;

import org.example.model.Book;
import org.example.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepo.findById(id);
    }

    public Book createBook(Book book) {
        return bookRepo.save(book);
    }

    public Book updateBook(Long id, Book updated) {
        return bookRepo.findById(id)
                .map(book -> {
                    book.setTitle(updated.getTitle());
                    book.setAuthor(updated.getAuthor());
                    book.setGenre(updated.getGenre());
                    book.setAvailable(updated.isAvailable());
                    return bookRepo.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Книгу не знайдено"));
    }

    public void deleteBook(Long id) {
        bookRepo.deleteById(id);
    }
}

