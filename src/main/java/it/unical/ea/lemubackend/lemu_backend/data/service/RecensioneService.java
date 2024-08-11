package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Recensione;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.RecensioneDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;

public interface RecensioneService {

    void save(Recensione recensione);

    Collection<RecensioneDto> findAll();

    RecensioneDto getById(Long id);

    List<RecensioneDto> findAllByIdProdotto(Long idProdotto);

    ResponseEntity<Page<RecensioneDto>> findAllByUtente(Utente utente, Pageable pageable);

}
