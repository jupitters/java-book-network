package com.jupitters.book_network.service;

import com.jupitters.book_network.dto.FeedbackRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;

public interface FeedbackService {
    Integer saveFeedback(FeedbackRequest request, Authentication connectedUser);
}
