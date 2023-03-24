package br.com.dias.moviecardgame.webapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;


/**
 * Class to configure Spring Security.
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.disabled}")
    private boolean disabled;

    /**
     * Customizing where to look for User Roles.
     *
     * @return {@link JwtAuthenticationConverter} with the customization
     */
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    /**
     * Configuring who can access the endpoints.
     * Basically: the 'ranking' can be access without authentication,
     * and 'game' endpoint methods can be access by logged user with PLAYER role.
     *
     * The authentication provider shipped by docker-compose has configuration to assign this role to all users.
     *
     * @param http the {@link HttpSecurity} to apply rules
     *
     * @throws Exception if some error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * Disabling the authentication and ROLE can be useful when using ApiGateway in prd.
         * Because it avoids having two places with the same configuration.
         */
        if (!disabled) {
            http.cors()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/ranking").permitAll()
                    .antMatchers("/game/**").hasAuthority("PLAYER")
                    .and()
                    .oauth2ResourceServer()
                    .jwt()
                    .jwtAuthenticationConverter(jwtAuthenticationConverter());
        } else {
            http.csrf().disable().cors()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .and()
                    .oauth2ResourceServer()
                    .jwt();
        }
    }

}
