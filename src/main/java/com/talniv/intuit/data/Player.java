package com.talniv.intuit.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Player {

    @Id
    private String playerId;

    @Column
    private Integer birthYear;

    @Column
    private Integer birthMonth;

    @Column
    private Integer birthDay;

    @Column
    private String birthCountry;

    @Column
    private String birthState;

    @Column
    private String birthCity;

    @Column
    private Integer deathYear;

    @Column
    private Integer deathMonth;

    @Column
    private Integer deathDay;

    @Column
    private String deathCountry;

    @Column
    private String deathState;

    @Column
    private String deathCity;

    @Column
    private String nameFirst;

    @Column
    private String nameLast;

    @Column
    private String nameGiven;

    @Column
    private Integer weight;

    @Column
    private Integer height;

    @Column
    private String bats;

    @Column
    private String throwsDirection;

    @Column
    private String debut;

    @Column
    private String finalGame;

    @Column
    private String retroId;

    @Column
    private String bbrefID;
}
