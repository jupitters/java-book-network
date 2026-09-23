package com.jupitters.book_network.repository;

import com.jupitters.book_network.model.BookTransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Integer> {
}
