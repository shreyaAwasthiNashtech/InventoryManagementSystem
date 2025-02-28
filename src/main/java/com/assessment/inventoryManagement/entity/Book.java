package com.assessment.inventoryManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    private String isbn;

    private String title;
    private String author;
    private String genre;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private int quantityInStock;
    private LocalDate publicationDate;
}
