package it.unical.ea.lemubackend.lemu_backend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
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
import org.springframework.security.web.DefaultSecurityFilterChain;
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
            "/utente-api/add",
            // Utente
            "/utente-api/register",
            "/utente-api/authenticate",
            "/utente-api/google_login",
            "/utente-api/facebook_login",
            "utente-api/search",
            // Recensioni
            "/recensione-api/getByIdProdotto/**",
            // Prodotto
            "/prodottocontroller-api/add",
            "/prodottocontroller-api/all",
            "/prodottocontroller-api/search",
            "/prodottocontroller-api/get/{id}",
            "/prodottocontroller-api/add",
            "prodottocontroller-api/get/by-category/{categoria}",
            //Wishlist
            "/wishlist-api/add",
            "/wishlist-api/update/{id}",
            "/wishlist-api/delete/{id}",
            "/wishlist-api/getByUtente/{utenteId}",
            "/wishlist-api/utente/{utenteId}",
            "wishlist-api/prodotti/add",
            "/wishlist-api/prodotti/update/{id}",
            "/wishlist-api/prodotti/delete/{id}",
            "/wishlist-api/prodotti/get/{id}",
            "/wishlist-api/prodotti/get/{wishlistid}/all",
            "/wishlistcondivisione-api/add",
            "/wishlistcondivisione-api/update/{id}",
            "/wishlistcondivisione-api/delete/{id}",
            "/wishlistcondivisione-api/get/{id}",
            "/wishlistcondivisione-api/all",
            "/wishlistcondivisione-api/wishlist/{wishlistId}",
            "/wishlistcondivisione-api/condivise",
            // Categorie
            "/categoria-api/get/{id}",

            "/swagger-ui/index.html",
            "/swagger-ui.html",
    };


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    private final RequestFilter requestFilter;

    public SecurityConfiguration(@Lazy RequestFilter requestFilter) {
        this.requestFilter = requestFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(PATH_WHITELIST).permitAll();
                    auth.anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .logout(logout -> logout
                        .permitAll()
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                ).addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
