package com.assessment.inventoryManagement.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private BigDecimal price;
    private int quantityInStock;
    private LocalDate publicationDate;
}
