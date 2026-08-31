package com.jupitters.book_network.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookTransactionHistory extends BaseEntity {
    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    private boolean returned;
    private boolean returnApproved;
}
