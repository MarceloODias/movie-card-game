package br.com.dias.moviecardgame.webapp.dto;

import br.com.dias.moviecardgame.abs.dto.RoundRequest;
import lombok.Builder;
import lombok.Data;

/**
 * Class to represent a Round answer.
 */
@Data
public class RoundRequestDto implements RoundRequest {

    /**
     * User answering.
     */
    private String userId;

    /**
     * MovieId answered.
     */
    private String answerMovieId;

}
