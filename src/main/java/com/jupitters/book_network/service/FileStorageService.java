package com.jupitters.book_network.service;

import com.jupitters.book_network.model.Book;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String saveFile(MultipartFile file, Integer userId);
}
