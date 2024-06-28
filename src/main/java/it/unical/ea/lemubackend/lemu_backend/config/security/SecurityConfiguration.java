package it.unical.ea.lemubackend.lemu_backend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {


    private static final String[] PATH_WHITELIST = {
            // -- Swagger UI v3
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // Registration
            "/utente-api/add",
            // Login
            "/utente-api/login",
            "/prodottocontroller-api/add",
            "/prodottocontroller-api/all",
            "/swagger-ui.html"
    };


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    private final RequestFilter requestFilter;

    public SecurityConfiguration(RequestFilter requestFilter) {
        this.requestFilter = requestFilter;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainJWT(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers(PATH_WHITELIST).permitAll();
                    auth.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable).formLogin(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    @Order(2)
    public SecurityFilterChain filterChainOAuth2(HttpSecurity http) throws Exception {
        return http.
                logout(
                        lgt -> {
                            lgt.permitAll();
                            lgt.invalidateHttpSession(true);
                            lgt.clearAuthentication(true);
                            lgt.deleteCookies("JSESSIONID");
                        }
                ).authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers(PATH_WHITELIST).permitAll();
                            auth.anyRequest().authenticated();
                        }
                ).oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults()).build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
