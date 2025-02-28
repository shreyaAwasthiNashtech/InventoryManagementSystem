package com.assessment.inventoryManagement.service;

import com.assessment.inventoryManagement.dto.BookDTO;

import java.util.List;

public interface BookService {
    List<BookDTO> getAllBooks();
    BookDTO addBook(BookDTO bookDTO);
    BookDTO getBookByISBN(String isbn);
    List<BookDTO> searchBooksByTitle(String title);
    BookDTO updateBook(String isbn, BookDTO bookDTO);
    void deleteBook(String isbn);
    void purchaseBook(String isbn, int quantity);
}
