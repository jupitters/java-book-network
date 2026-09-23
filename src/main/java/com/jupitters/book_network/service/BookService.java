package com.jupitters.book_network.service;

import com.jupitters.book_network.dto.BookRequest;
import com.jupitters.book_network.dto.BookResponse;
import com.jupitters.book_network.dto.BorrowedBookResponse;
import com.jupitters.book_network.dto.PageResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;

public interface BookService {
    Integer saveBook(BookRequest request, Authentication connectedUser);
    BookResponse findById(Integer bookId);

    PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser);

    PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser);

    PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser);

     PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser);

    Integer updateShareableStatus(Integer bookId, Authentication connectedUser);

    Integer updateArchivedStatus(Integer bookId, Authentication connectedUser);

    Integer borrowBook(Integer bookId, Authentication connectedUser);
}
