package com.jupitters.book_network.service.impl;

import com.jupitters.book_network.dto.FeedbackRequest;
import com.jupitters.book_network.exception.OperationNotPermittedException;
import com.jupitters.book_network.model.Book;
import com.jupitters.book_network.model.Feedback;
import com.jupitters.book_network.model.User;
import com.jupitters.book_network.repository.BookRepository;
import com.jupitters.book_network.repository.FeedbackRepository;
import com.jupitters.book_network.service.FeedbackService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final BookRepository bookRepository;
    private final FeedbackRepository feedbackRepository;

    @Override
    public Integer saveFeedback(FeedbackRequest request, Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found with specified id!"));
        if(book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("You cannot give a feedback to this book.");
        }
        User user = (User) connectedUser.getPrincipal();
        if(Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You cannot give a feedback to your own book.");
        }
        Feedback feedback = toFeedback(request);

        return feedbackRepository.save(feedback).getId();
    }

    private Feedback toFeedback(FeedbackRequest request) {
        return Feedback.builder()
                .note(request.note())
                .comment(request.comment())
                .book(Book.builder()
                        .id(request.bookId())
                        .build())
                .build();
    }
}
