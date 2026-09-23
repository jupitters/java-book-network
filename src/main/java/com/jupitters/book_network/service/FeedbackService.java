package com.jupitters.book_network.service;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;

public interface FeedbackService {
    Integer saveFeedback(FeedbackRequest request, Authentication connectedUser);
}
