package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.dao.OrdineDao;
import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Ordine;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.OrdineDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdineServiceImpl implements OrdineService {

    private final OrdineDao ordineDao;
    private final ModelMapper modelMapper;
    private final UtenteDao utenteDao;

    @Override
    public OrdineDto save(OrdineDto ordineDto, String encodedJwt) {
        try {
            // Decodifica del JWT base64 per ottenere il token JWT effettivo
            String jwt = new String(Base64.getDecoder().decode(encodedJwt));

            // Ottiene l'username (o email) dall'oggetto JWT usando il metodo TokenStore.getUser
            String username = TokenStore.getInstance().getUser(jwt);

            // Trova l'utente in base all'email
            Utente utente = utenteDao.findByCredenzialiEmail(username).orElse(null);

            // Se l'utente esiste, salva l'ordine
            if (utente != null) {
                // Mappa OrdineDto a Ordine
                Ordine ordine = modelMapper.map(ordineDto, Ordine.class);
                ordine.setUtente(utente); // Associa l'utente all'ordine

                // Salva l'ordine nel database
                ordine = ordineDao.save(ordine);

                // Mappa l'Ordine salvato di nuovo a OrdineDto e restituiscilo
                return modelMapper.map(ordine, OrdineDto.class);
            } else {
                // Se l'utente non esiste, restituisce null
                return null;
            }
        } catch (Exception e) {
            // Se si verifica un'eccezione, gestiscila o registrala se necessario
            e.printStackTrace(); // Puoi gestire le eccezioni in modo più appropriato a seconda del tuo caso d'uso
            return null;
        }
    }


    @Override
    public OrdineDto getById(Long id) {
        Ordine ordine = ordineDao.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Non esiste un articolo con id: [%s]", id)));
        return modelMapper.map(ordine, OrdineDto.class);
    }


    @Override
    public Collection<OrdineDto> findOrderbyUser(String encodedJwt) {
        // Decodifica del JWT base64 per ottenere il token JWT effettivo
        String jwt = new String(Base64.getDecoder().decode(encodedJwt));

        // Crea un CompletableFuture per gestire l'operazione asincrona
        CompletableFuture<List<OrdineDto>> ordini = new CompletableFuture<>();

        // Verifica il token e ottieni l'email dell'utente
        try {
            // Ottiene l'username (o email) dall'oggetto JWT usando il metodo TokenStore.getUser
            String email = TokenStore.getInstance().getUser(jwt);

            // Trova l'utente in base all'email
            Utente utente = utenteDao.findByCredenzialiEmail(email).orElse(null);

            // Se l'utente esiste, recupera i suoi ordini
            if (utente != null) {
                // Ottieni tutti gli ordini dell'utente e mappa ciascun ordine a OrdineDto
                List<OrdineDto> ordineDtos = ordineDao.findByUtente(utente).stream()
                        .map(ordine -> modelMapper.map(ordine, OrdineDto.class))
                        .collect(Collectors.toList());

                // Completa il CompletableFuture con la lista di OrdineDto
                ordini.complete(ordineDtos);
            } else {
                // Se l'utente non esiste, completa con un'eccezione
                ordini.completeExceptionally(new Exception("Utente non trovato"));
            }
        } catch (Exception e) {
            // Se si verifica un'eccezione, completa il CompletableFuture con un'eccezione
            ordini.completeExceptionally(e);
        }

        // Attende il completamento del CompletableFuture e restituisce il risultato
        return ordini.join();
    }

}
