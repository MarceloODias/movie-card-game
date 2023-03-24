package br.com.dias.moviecardgame.webapp;

import br.com.dias.moviecardgame.webapp.config.ApplicationConfiguration;
import br.com.dias.moviecardgame.webapp.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Main spring boot startup class.
 * This is the application entry point.
 */
@SpringBootApplication
@Import({ ApplicationConfiguration.class, SecurityConfig.class })
public class Application {

    /**
     * Entrypoint.
     *
     * @param args arguments passed
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}
