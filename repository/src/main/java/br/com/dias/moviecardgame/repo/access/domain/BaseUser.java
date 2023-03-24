package br.com.dias.moviecardgame.repo.access.domain;

import br.com.dias.moviecardgame.abs.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity mapping User table.
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "User")
public class BaseUser implements User {

    /**
     * Identifier
     */
    @Id
    private String id;

    /**
     * Name
     */
    private String name;

    /**
     * Rounds played
     */
    private int rounds;

    /**
     * Rounds won
     */
    private int roundsWon;

    /**
     * Points accumulated
     */
    private int points;

    /**
     * Flag to indicate if the current game is playable
     */
    private boolean finished;

    /**
     * Default constructor used through builder.
     */
    public BaseUser() {

    }
}
