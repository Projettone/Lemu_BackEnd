package it.unical.ea.lemubackend.lemu_backend.controller;

import com.nimbusds.jose.JOSEException;
import it.unical.ea.lemubackend.lemu_backend.config.ApiResponse;
import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
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
import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping(path="/utente-api")
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
    public ResponseEntity<ApiResponse<String>> authenticate(@RequestParam("email") String email, @RequestParam("password") String password) throws JOSEException {
        System.out.println("PROCEDO CON AUTENTICATIOZE");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String token = TokenStore.getInstance().createToken(Map.of("email", email));
        return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK.toString(), token));
    }


    @PostMapping("/register")
    public ApiResponse<String> registerUser(@RequestBody UtenteRegistrazioneDto utenteRegistrazioneDto) {
        try {
            ResponseEntity<?> response = utenteService.registerUser(utenteRegistrazioneDto);
            String token = TokenStore.getInstance().extractToken(response);
            return new ApiResponse<>(true, response.getStatusCode().toString(), token);
        } catch (Exception e) {
            return new ApiResponse<>(false, HttpStatus.BAD_REQUEST.toString(), "Error: " + e.getMessage());
        }
    }



}
