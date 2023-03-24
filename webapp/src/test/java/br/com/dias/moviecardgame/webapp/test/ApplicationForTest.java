package br.com.dias.moviecardgame.webapp.test;

import br.com.dias.moviecardgame.webapp.config.ApplicationConfiguration;
import br.com.dias.moviecardgame.webapp.config.test.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ ApplicationConfiguration.class, SecurityConfig.class })
@ComponentScan(basePackages = "br.com.dias.moviecardgame.webapp.controller")
public class ApplicationForTest {

    /**
     * Entrypoint.
     *
     * @param args arguments passed
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationForTest.class, args);
    }

}