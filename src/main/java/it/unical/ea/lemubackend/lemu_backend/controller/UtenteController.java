package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.*;
import it.unical.ea.lemubackend.lemu_backend.data.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/user-api")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UtenteController {

    private UtenteDao utenteDao;


    public UtenteController(UtenteDao utenteDao) { this.utenteDao = utenteDao; }

    @GetMapping(path="/google_login")
    public ResponseEntity<String> register(Model model, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                           @AuthenticationPrincipal OAuth2User oauth2User) {
        model.addAttribute("userName", oauth2User.getName());
        model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
        model.addAttribute("userAttributes", oauth2User.getAttributes());

        Optional<Utente> optionalUtente = utenteDao.findByCredenzialiEmail(oauth2User.getName());
        Utente utente;
        if(optionalUtente == null) {
            utente = new Utente(oauth2User.getAttributes().get("email").toString(),
                    oauth2User.getAttributes().get("given_name").toString(),
                    oauth2User.getAttributes().get("family_name").toString());
            utenteDao.save(utente);
            return new ResponseEntity<>(utente.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>(optionalUtente.toString(), HttpStatus.OK);
    }

}
