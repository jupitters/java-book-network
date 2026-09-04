package com.jupitters.book_network.service;

import com.jupitters.book_network.dto.BookRequest;
import com.jupitters.book_network.dto.BookResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;

public interface BookService {
    Integer saveBook(BookRequest request, Authentication connectedUser);
    BookResponse findById(Integer bookId);

    PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser);
}
