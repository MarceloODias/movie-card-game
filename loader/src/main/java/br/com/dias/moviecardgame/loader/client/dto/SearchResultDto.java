package br.com.dias.moviecardgame.loader.client.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Dto class used to deserialize Movie search data API.
 */
@Getter
@Setter
public class SearchResultDto {

    /**
     * Movies returned by the search API
     */
    @SerializedName("Search")
    private List<MovieDto> movies;

    /**
     * Quantity found by the search
     */
    private Long totalResults;

}
