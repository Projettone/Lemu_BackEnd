package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.dao.ProdottoDao;
import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Prodotto;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.ProdottoDto;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdottoServiceImpl implements  ProdottoService {

    private final ProdottoDao prodottoDao;
    private final UtenteDao utenteDao;
    private final ModelMapper modelMapper;
    private final UtenteService utenteService;




    @Override
    public ProdottoDto save(ProdottoDto prodottoDto, String encodedJwt) {
        /*
        try {
            // Decodifica del JWT
            String jwt = new String(Base64.getDecoder().decode(encodedJwt));

            // Trova l'utente venditore
            Utente venditore = utenteDao.findById(prodottoDto.getUtente().getId()).orElse(null);

            // Verifica il token e confronta l'email
            String username = TokenStore.getInstance().getUser(jwt);
            if (username != null && venditore != null && username.equals(venditore.getCredenziali().getEmail())) {
                // Mappa ProdottoDto a Prodotto
                Prodotto prodotto = modelMapper.map(prodottoDto, Prodotto.class);
                prodotto.setUtente(venditore); // Associa l'utente al prodotto

                // Salva il prodotto nel database
                prodotto = prodottoDao.save(prodotto);

                // Mappa il Prodotto salvato di nuovo a ProdottoDto e restituiscilo
                return modelMapper.map(prodotto, ProdottoDto.class);
            } else {
                // Se la verifica fallisce, restituisce null
                return null;
            }
        } catch (Exception e) {
            // Gestione dell'eccezione
            e.printStackTrace(); // Puoi gestire le eccezioni in modo più appropriato a seconda del tuo caso d'uso
            return null;
        }

         */
        return  null;
    }


    @Override
    public Collection<ProdottoDto> findAll() {
        return prodottoDao.findAll().stream()
                .map(u -> modelMapper.map(u, ProdottoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProdottoDto getById(Long id) {
        Prodotto prodotto = prodottoDao.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Non esiste un prodotto con id: [%s]", id)));
        return modelMapper.map(prodotto, ProdottoDto.class);
    }

    @Override
    public void save(ProdottoDto prodottoDto) {
        Prodotto p = modelMapper.map(prodottoDto, Prodotto.class);
        p.setImmagineBase64(prodottoDto.getImmagineProdotto());
        UtenteDto utenteDto = utenteService.getById(prodottoDto.getIdutente());
        Utente utente =  modelMapper.map(utenteDto, Utente.class);
        p.setUtente(utente);
        prodottoDao.save(p);
    }


}
