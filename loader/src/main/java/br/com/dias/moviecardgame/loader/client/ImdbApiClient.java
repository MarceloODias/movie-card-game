package br.com.dias.moviecardgame.loader.client;

import br.com.dias.moviecardgame.abs.client.MovieApi;
import br.com.dias.moviecardgame.abs.domain.Movie;
import br.com.dias.moviecardgame.loader.client.dto.MovieDto;
import br.com.dias.moviecardgame.loader.client.dto.SearchResultDto;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard implementation class of {@link MovieApi}.
 * This implementation is coupled to Imdb api.
 */
public class ImdbApiClient implements MovieApi {

    private static final String ERROR_MESSAGE = "Error while requesting Imdb API";
    private static final Logger LOG = LoggerFactory.getLogger(ImdbApiClient.class);

    private final Gson gson;
    private final String apiKey;
    private final String endpoint;
    private final HttpClient httpClient;

    /**
     * Constructor to inject dependencies.
     *
     * @param gson {@link Gson} to deserialize responses
     * @param endpoint api address to use
     * @param apiKey the api key to use
     */
    public ImdbApiClient(Gson gson, String endpoint, String apiKey){
        this.gson = gson;
        this.apiKey = apiKey;
        this.endpoint = endpoint;

        this.httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    }

    /** {@inheritDoc} */
    @Override
    public List<Movie> getMovies(String search, long limit) {
        LOG.info("Loading movies with search {}", search);

        ArrayList<Movie> movies = new ArrayList<>();
        try {

            long count = 0;
            long page = 1;
            long max;

            do {
                String listEndpoint = String.format("%s?apikey=%s&type=movie&s=%s&page=%s", endpoint, apiKey, search, page);
                SearchResultDto moviesIndex = (SearchResultDto) performRequest(listEndpoint, getHeaders(), SearchResultDto.class);
                max = moviesIndex.getTotalResults();

                for (Movie movieIndex : moviesIndex.getMovies()) {
                    try {
                        String getEndpoint = String.format("%s?apikey=%s&plot=short&i=%s", endpoint, apiKey, movieIndex.getId());
                        Movie movie = (Movie) performRequest(getEndpoint, getHeaders(), MovieDto.class);
                        movies.add(movie);

                        count++;
                        if (count == limit) {
                            break;
                        }
                    } catch (Exception ex) {
                        LOG.warn("Movie ignored. Exception occurred loading movie {}", movieIndex.getId());
                        return movies;
                    }
                }
                page++;
            } while (count != limit && count != max);

        } catch (Exception e) {
            LOG.warn("Error occurred loading movies", e);
        }

        return movies;
    }

    /**
     * Gets default headers.
     *
     * @return the headers
     */
    private String[] getHeaders(){
        return new String[] {
                "Content-Type", "application/json"};
    }

    /**
     * Performs the http request to api.
     *
     * @param endpoint api address used
     * @param headers headers to send
     * @param responseClass class to map the response
     *
     * @return the api response mapped using responseClass
     * @throws Exception if some error occurs calling the api
     */
    private Object performRequest(String endpoint, String[] headers, Type responseClass) throws Exception {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Request to {}", endpoint);
        }

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .headers(headers)
                .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (LOG.isDebugEnabled()) {
                LOG.debug("Response from {}: {}/{}", endpoint, response, response.body());
            }

            if (response.statusCode() < 200 || response.statusCode() > 299) {
                throw new Exception(String.format("Error Status code %s - %s", response.statusCode(), response.body()));
            }
            return gson.fromJson(response.body(), responseClass);

        } catch (InterruptedException e) {
            LOG.error(ERROR_MESSAGE, e);
            Thread.currentThread().interrupt();
            return null;
        }
    }

}
