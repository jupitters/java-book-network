package com.jupitters.book_network.repository;

import com.jupitters.book_network.model.Book;
import com.jupitters.book_network.model.BookTransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Integer> {
    @Query("""
        SELECT h FROM BookTransactionHistory h
        WHERE h.user.id = :userId
        """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer userId);

    @Query("""
        SELECT h FROM BookTransactionHistory h
        WHERE h.book.owner.id = :userId
        """)
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, Integer userId);

    @Query("""
        SELECT (COUNT(*) > 0) AS isBorrowed
        FROM BookTransactionHistory bth
        WHERE bth.user.id = :userId
        AND bth.book.id = :bookId
        AND bth.returnApproved = false
        """)
    boolean isAlreadyBorrowedByUser(Integer bookId, Integer userId);

    @Query("""
        SELECT t
        FROM BookTransactionHistory t
        WHERE t.user.id = :userId
        AND t.book.id = :bookId
        AND t.returned = false
        AND t.returnApproved = false
        """)
    Optional<BookTransactionHistory> findByBookIdAndUserId(Integer bookId, Integer id);
}
