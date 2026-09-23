package com.jupitters.book_network.utils;

import com.jupitters.book_network.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> withOwnerId(Integer ownerId) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }
}
