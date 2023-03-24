package br.com.dias.moviecardgame.webapp.config.test;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Security configs used for integrations tests.
 */
public class SecurityConfig {

    /**
     * Configuring who can access the endpoints.
     * Basically: the 'ranking' can be access without authentication,
     * and 'game' endpoint methods can be access by logged user with PLAYER role.
     *
     * @param http the {@link HttpSecurity} to apply rules
     *
     * @throws Exception if some error occurs
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/game/**")
                .hasAuthority("PLAYER")
                .antMatchers("/ranking")
                .permitAll()
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }

}
