package com.jupitters.book_network.service.impl;

import com.jupitters.book_network.model.Book;
import com.jupitters.book_network.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    @Override
    public String saveFile(MultipartFile file, Book book, Integer userId) {
        return "";
    }
}
