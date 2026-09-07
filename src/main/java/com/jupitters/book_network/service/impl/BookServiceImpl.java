package com.jupitters.book_network.service.impl;

import com.jupitters.book_network.dto.BookRequest;
import com.jupitters.book_network.dto.BookResponse;
import com.jupitters.book_network.dto.PageResponse;
import com.jupitters.book_network.model.Book;
import com.jupitters.book_network.model.User;
import com.jupitters.book_network.repository.BookRepository;
import com.jupitters.book_network.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        return bookRepository.save(book).getId();
    }

    @Override
    public BookResponse findById(Integer bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        return toBookResponse(book);

    }

    @Override
    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());
    }

    private BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .synopsys(book.getSynopsis())
                .rate(book.getRate())
                .archived(book.isArchived())
                .shareable(book.isShareable())
                .owner(book.getOwner().getFullName())
                //.cover
                .build();
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
