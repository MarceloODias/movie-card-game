package br.com.dias.moviecardgame.loader.client.dto;

import br.com.dias.moviecardgame.abs.domain.Movie;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Dto class used to deserialize Movie data.
 */
@Data
public class MovieDto implements Movie {

    /**
     * Identifier
     */
    @SerializedName("imdbID")
    private String id;

    /**
     * Title
     */
    @SerializedName("Title")
    private String title;

    /**
     * Movie poster image web URI
     */
    @SerializedName("Poster")
    private String poster;

    /**
     * Rating obtained so far.
     */
    @SerializedName("imdbRating")
    @JsonIgnore
    private Double rating;

    /**
     * Votes obtained so far.
     */
    @SerializedName("imdbVotes")
    @JsonIgnore
    private String imdbVotes;

    /**
     * Votes obtained so far represented as number.
     */
    @JsonIgnore
    private Long votes;

    /**
     * Gets the votes obtained so far represented as number.
     * If the votes represented as number was not calculated, it will be calculated calling this method.
     *
     * @return the votes
     */
    public Long getVotes(){
        if (votes == null) {
            imdbVotes = imdbVotes.replaceAll(",", "");
            this.votes = Long.parseLong(imdbVotes);
        }
        return votes;
    }

}
