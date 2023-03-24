package br.com.dias.moviecardgame.webapp.controller;

import br.com.dias.moviecardgame.abs.service.MovieCachedService;
import br.com.dias.moviecardgame.webapp.dto.HealthDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Health controller to check readness and liveness.
 */
@RestController
@EnableWebMvc
public class HealthController {

    private final MovieCachedService cachedService;

    /**
     * Constructor to inject dependency
     *
     * @param cachedService the cached movie service
     */
    public HealthController(MovieCachedService cachedService){
        this.cachedService = cachedService;
    }

    /**
     * Checks the health of dependencies.
     * Real world applications needs to check all its dependencies, for example database.
     *
     * @return the health indicator
     */
    @RequestMapping(path = "/heartbeat",
            method = RequestMethod.GET,
            produces = {"application/json"})
    public  ResponseEntity<HealthDto> heartbeat() {
        if (cachedService.isLoaded()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(HealthDto.builder().status(HealthDto.Status.UP).message("All good.").build());
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(HealthDto.builder().status(HealthDto.Status.DOWN).message("Cache not loaded.").build());
        }
    }


}
