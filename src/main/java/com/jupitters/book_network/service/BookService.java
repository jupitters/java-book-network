package com.jupitters.book_network.service;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;

public interface BookService {
    Integer saveBook(BookRequest request, Authentication connectedUser);
}
