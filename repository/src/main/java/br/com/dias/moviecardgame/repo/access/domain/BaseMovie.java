package br.com.dias.moviecardgame.repo.access.domain;

import br.com.dias.moviecardgame.abs.domain.Movie;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity mapping Movie table.
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "Movie")
public class BaseMovie implements Movie {

    /**
     * Identifier
     */
    @Id
    private String id;

    /**
     * Title
     */
    private String title;

    /**
     * Poster image path
     */
    private String poster;

    /**
     * Current rating
     */
    private Double rating;

    /**
     * Current votes obtained
     */
    private Long votes;

    /**
     * Default constructor to be used by the Builder.
     */
    public BaseMovie() {

    }

    /**
     * Custom builder implementation.
     * This method is used when changing the concrete class is necessary.
     *
     * @param movie The movie used to copy values from
     * @return an instance of {@link BaseMovie}
     */
    public static BaseMovieBuilder builder(Movie movie){
        return new BaseMovieBuilder()
                .id(movie.getId())
                .title(movie.getTitle())
                .poster(movie.getPoster())
                .rating(movie.getRating());
    }
}
