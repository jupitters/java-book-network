package com.jupitters.book_network.service;

import com.jupitters.book_network.dto.BookRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;

public interface BookService {
    Integer saveBook(BookRequest request, Authentication connectedUser);
}
