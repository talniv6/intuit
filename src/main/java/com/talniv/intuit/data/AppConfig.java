package com.talniv.intuit.data;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AppConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String latestContentHash;
}
