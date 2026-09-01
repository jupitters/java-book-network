package com.jupitters.book_network.repository;

import com.jupitters.book_network.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
