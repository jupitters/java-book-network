package com.jupitters.book_network.service.impl;

import com.jupitters.book_network.dto.BookRequest;
import com.jupitters.book_network.model.Book;
import com.jupitters.book_network.model.User;
import com.jupitters.book_network.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Integer saveBook(BookRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Book book = toBook(request);
        book.setOwner(user);
        bookRepository.save(book);

        return 0;
    }

    private Book toBook(BookRequest request) {
        return Book.builder()
                .id(request.id())
                .title(request.title())
                .authorName(request.authorName())
                .synopsis(request.synopsys())
                .archived(false)
                .shareable(request.shareable())
                .build();
    }
}
