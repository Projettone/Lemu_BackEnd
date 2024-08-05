package it.unical.ea.lemubackend.lemu_backend.data.service;

import com.nimbusds.jose.JOSEException;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Indirizzo;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteDto;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteRegistrazioneDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

public interface UtenteService {

    public void save(Utente utente);

    ResponseEntity<?> registerUser(UtenteRegistrazioneDto utenteRegistrazioneDto) throws JOSEException;

    UtenteDto getById(Long id);

    UtenteDto getByCEmail(String email);

    ResponseEntity<?> googleAuthentication(String idToken) throws JOSEException, GeneralSecurityException, IOException;

    UtenteDto getUserByToken(String token) throws ParseException, JOSEException;

    void updatePassword(String token, String newPassword) throws ParseException, JOSEException;
    void updateShippingAddress(String token, Indirizzo address) throws ParseException, JOSEException;

}
