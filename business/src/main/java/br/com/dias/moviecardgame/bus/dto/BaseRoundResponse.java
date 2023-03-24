package br.com.dias.moviecardgame.bus.dto;

import br.com.dias.moviecardgame.abs.domain.Movie;
import br.com.dias.moviecardgame.abs.dto.RoundResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Standard implementation class of {@link RoundResponse}.
 */
@Data
@Builder
public class BaseRoundResponse implements RoundResponse {

    /**
     * Movies returned to user.
     */
    private List<Movie> movies;

    /**
     * The round number user is.
     */
    private int roundNumber;

    /**
     * Quantity of errors made so far.
     */
    private int errorCount;

    /**
     * Message returned.
     */
    private String message;

    /**
     * Quantity of points made so far.
     */
    private int pointsAccumulated;

}
