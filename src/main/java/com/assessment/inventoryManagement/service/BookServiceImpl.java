package com.assessment.inventoryManagement.service;

import com.assessment.inventoryManagement.dto.BookDTO;
import com.assessment.inventoryManagement.entity.Book;
import com.assessment.inventoryManagement.exception.ResourceNotFoundException;
import com.assessment.inventoryManagement.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream().map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        logger.info("Adding book: {}", bookDTO.getTitle());
        Book book = modelMapper.map(bookDTO, Book.class);
        bookRepository.save(book);
        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    public BookDTO getBookByISBN(String isbn) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    public List<BookDTO> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream().map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO updateBook(String isbn, BookDTO bookDTO) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));

        modelMapper.map(bookDTO, book);
        bookRepository.save(book);
        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }

    @Override
    @Transactional
    public void purchaseBook(String isbn, int quantity) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));

        if (book.getQuantityInStock() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for book: " + book.getTitle());
        }

        book.setQuantityInStock(book.getQuantityInStock() - quantity);
        bookRepository.save(book);
    }
}
