package com.jupitters.book_network.service.impl;

import com.jupitters.book_network.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    @Override
    public Integer saveBook(BookRequest request, Authentication connectedUser) {
        return 0;
    }
}
