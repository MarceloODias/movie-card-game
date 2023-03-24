package br.com.dias.moviecardgame.abs.domain;

/**
 * Interface to abstract the Movie class.
 */
public interface Movie {

    /**
     * Gets the identifier.
     *
     * @return the identifier
     */
    String getId();

    /**
     * Sets the identifier.
     *
     * @param id the identifier
     */
    void setId(String id);

    /**
     * Gets the Movie title.
     *
     * @return the Movie title
     */
    String getTitle();

    /**
     * Sets the Movie title.
     *
     * @param title the movie title
     */
    void setTitle(String title);

    /**
     * Gets the Movie poster URI image.
     *
     * @return the Movie poster URI image
     */
    String getPoster();

    /**
     * Sets the Movie poster URI image.
     *
     * @param poster the Movie poster URI image
     */
    void setPoster(String poster);

    /**
     * Gets the Movie rating value.
     *
     * @return the Movie rating value
     */
    Double getRating();

    /**
     * Sets the Movie rating value.
     *
     * @param rating the Movie rating value
     */
    void setRating(Double rating);

    /**
     * Gets the quantity of votes of a Movie.
     *
     * @return the Movie quantity of votes
     */
    Long getVotes();

    /**
     * Sets the quantity of votes of a Movie.
     *
     * @param votes the Movie quantity of votes
     */
    void setVotes(Long votes);

}
