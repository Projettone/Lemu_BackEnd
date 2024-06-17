package it.unical.ea.lemubackend.lemu_backend.controller;

import com.nimbusds.jose.JOSEException;
import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.data.service.UtenteService;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteRegistrazioneDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;



import java.util.Map;

@RequestMapping(path="/api/v1", produces = "application/json")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class UtenteController {

    private final UtenteDao utenteDao;
    private final UtenteService utenteService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;



    public UtenteController(UtenteDao utenteDao, UtenteService utenteService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.utenteDao = utenteDao;
        this.utenteService = utenteService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(path="/google_login")
    public ResponseEntity<UtenteRegistrazioneDto> googleAuthentication(Model model, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                                                       @AuthenticationPrincipal OAuth2User oauth2User) {
        return ResponseEntity.ok(this.utenteService.googleAuthentication(model, authorizedClient, oauth2User));
    }


    @PostMapping(path = "/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public void authenticate(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletResponse response) throws JOSEException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String token = TokenStore.getInstance().createToken(Map.of("username",  email));
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    @PostMapping(path="/register")
    public ResponseEntity<String> register(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        System.out.println("ciao");
        if("admin".equals(email) || utenteDao.findByCredenzialiEmail(email).isPresent())
            return new ResponseEntity<>("existing username", HttpStatus.CONFLICT);
        Utente utente = new Utente(email, passwordEncoder.encode(password), "");
        utenteDao.save(utente);
        return new ResponseEntity<>("registered", HttpStatus.OK);
    }





}
