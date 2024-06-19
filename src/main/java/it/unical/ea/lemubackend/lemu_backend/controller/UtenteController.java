package it.unical.ea.lemubackend.lemu_backend.controller;

import com.nimbusds.jose.JOSEException;
import it.unical.ea.lemubackend.lemu_backend.config.ApiResponse;
import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.data.service.UtenteService;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteLoginDto;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteRegistrazioneDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RequiredArgsConstructor
@RequestMapping(path="/api/v1")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UtenteController {

    private final UtenteDao utenteDao;
    private final UtenteService utenteService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    /*
    @GetMapping(path="/google_login")
    public ResponseEntity<UtenteRegistrazioneDto> googleAuthentication(Model model, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                                                       @AuthenticationPrincipal OAuth2User oauth2User) {
        return ResponseEntity.ok(this.utenteService.googleAuthentication(model, authorizedClient, oauth2User));
    }

     */


    @PostMapping(path = "/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public void authenticate(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletResponse response) throws JOSEException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String token = TokenStore.getInstance().createToken(Map.of("username",  email));
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Utente>> register(@RequestBody UtenteRegistrazioneDto utenteRegistrazioneDto) {
        String credenzialiEmail = utenteRegistrazioneDto.getCredenzialiEmail();
        String credenzialiPassword = utenteRegistrazioneDto.getCredenzialiPassword();
        String nome = utenteRegistrazioneDto.getNome();
        String cognome = utenteRegistrazioneDto.getCognome();

        if (utenteDao.findByCredenzialiEmail(credenzialiEmail).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, "Username già esistente", null));
        }

        Utente utente = new Utente(credenzialiEmail, passwordEncoder.encode(credenzialiPassword), nome, cognome);
        utenteDao.save(utente);
        return ResponseEntity.ok(new ApiResponse<>(true, "Registrazione avvenuta con successo", utente));
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody UtenteLoginDto utenteLoginDto) throws JOSEException {
        String credenzialiEmail = utenteLoginDto.getCredenzialiEmail();
        String credenzialiPassword = utenteLoginDto.getCredenzialiPassword();

        if (utenteDao.findByCredenzialiEmail(credenzialiEmail).isPresent()) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credenzialiEmail, credenzialiPassword));
            String token = TokenStore.getInstance().createToken(Map.of("email",  credenzialiEmail));
            return ResponseEntity.ok(new ApiResponse<>(true, "Login effettuato con successo", token));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Errore, credenziali errate", null));
        }
    }



}
