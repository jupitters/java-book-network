package com.jupitters.book_network.service;

import com.jupitters.book_network.dto.FeedbackRequest;
import com.jupitters.book_network.dto.FeedbackResponse;
import com.jupitters.book_network.dto.PageResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;

public interface FeedbackService {
    Integer saveFeedback(FeedbackRequest request, Authentication connectedUser);

    PageResponse<FeedbackResponse> findAllFeedbacksByBook(Integer bookId, int page, int size, Authentication connectedUser);
}
