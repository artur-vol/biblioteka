package org.example.service;

import org.example.model.Book;
import org.example.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }

    // Тест повернення всіх книг
    @Test
    void shouldReturnAllBooks() {
        List<Book> books = Arrays.asList(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        verify(bookRepository).findAll();
    }

    // Тест пошуку книги за ID
    @Test
    void shouldReturnBookById() {
        Book book = new Book();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1L);

        assertTrue(result.isPresent());
        verify(bookRepository).findById(1L);
    }

    // Тест створення нової книги
    @Test
    void shouldCreateBook() {
        Book book = new Book();
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.createBook(book);

        assertEquals(book, result);
        verify(bookRepository).save(book);
    }

    // Тест оновлення книги, яка існує
    @Test
    void shouldUpdateBook_whenExists() {
        Book existing = new Book("Old", "Author", "Genre", true);
        Book updated = new Book("New", "Other", "Sci-Fi", false);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookRepository.save(any(Book.class))).thenReturn(existing);

        Book result = bookService.updateBook(1L, updated);

        assertEquals("New", result.getTitle());
        assertEquals("Other", result.getAuthor());
        assertFalse(result.isAvailable());
        verify(bookRepository).save(existing);
    }

    // Тест винятку при оновленні неіснуючої книги
    @Test
    void shouldThrow_whenBookToUpdateNotFound() {
        Book updated = new Book("New", "Other", "Sci-Fi", false);
        when(bookRepository.findById(42L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                bookService.updateBook(42L, updated));

        assertEquals("Книгу не знайдено", ex.getMessage());
    }

    // Тест видалення книги за ID
    @Test
    void shouldDeleteBook() {
        bookService.deleteBook(1L);
        verify(bookRepository).deleteById(1L);
    }
}
