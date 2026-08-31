package com.jupitters.book_network.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Feedback {
    @Id
    @GeneratedValue
    private Integer id;

    private Double note;
    private String comment;
}
