package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteDto;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteRegistrazioneDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;

public interface UtenteService {

    public void save(Utente utente);

    UtenteDto getById(Long id);

    UtenteDto getByCEmail(String email);

    UtenteRegistrazioneDto googleAuthentication(Model model,
                                                @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                                @AuthenticationPrincipal OAuth2User oauth2Use);



}
