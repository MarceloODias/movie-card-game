package br.com.dias.moviecardgame.repo.access.domain;

import br.com.dias.moviecardgame.abs.domain.Round;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity mapping Round table.
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "Round")
public class BaseRound implements Round {

    /**
     * Identifier
     */
    @Id
    private String id;

    /**
     * Flag to check if the pair of movies was already used
     */
    private boolean used;

    /**
     * Flag to check if the round was answered
     */
    private boolean answered;

    /**
     * The user id playing this round
     */
    private String userId;

    /**
     * Winner Movie id of this round
     */
    private String movieIdToWin;

    /**
     * Default constructor used through the builder.
     */
    public BaseRound() {

    }
}
