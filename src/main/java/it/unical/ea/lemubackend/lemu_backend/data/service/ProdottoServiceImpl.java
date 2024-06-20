package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.dao.ProdottoDao;
import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Prodotto;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.ProdottoDto;
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




    @Override
    public ProdottoDto save(ProdottoDto prodottoDto, String encodedJwt) {
        // Decodifica del JWT
        String jwt = new String(Base64.getDecoder().decode(encodedJwt));

        CompletableFuture<ProdottoDto> res = new CompletableFuture<>();

        // Mappa ArticoloDto a Articolo
        Prodotto prodotto = modelMapper.map(prodottoDto, Prodotto.class);

        // Trova l'utente venditore
        Utente venditore = utenteDao.findById(prodottoDto.getUtente().getId()).orElse(null);

        // Verifica il token e confronta l'email
        try {
            String username = TokenStore.getInstance().getUser(jwt);
            if (username != null && venditore != null && username.equals(venditore.getCredenziali().getEmail())) {
                prodottoDao.save(prodotto);
                res.complete(modelMapper.map(prodotto, ProdottoDto.class));
            } else {
                res.complete(null);
            }
        } catch (Exception e) {
            res.completeExceptionally(e);
        }

        return res.join();
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


}
