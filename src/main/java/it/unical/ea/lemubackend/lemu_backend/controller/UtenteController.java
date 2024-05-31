package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.data.service.UtenteService;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteRegistrazioneDto;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequestMapping(path="/api/v1", produces = "application/json")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class UtenteController {

    private final UtenteService utenteService;


    public UtenteController(UtenteService utenteService) { this.utenteService = utenteService; }

    @GetMapping(path="/google_login")
    public ResponseEntity<UtenteRegistrazioneDto> googleAuthentication(Model model, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                                                       @AuthenticationPrincipal OAuth2User oauth2User) {
        return ResponseEntity.ok(this.utenteService.googleAuthentication(model, authorizedClient, oauth2User));
    }



}
