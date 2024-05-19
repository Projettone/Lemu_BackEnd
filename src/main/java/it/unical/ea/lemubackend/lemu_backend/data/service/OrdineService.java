package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Ordine;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Prodotto;
import it.unical.ea.lemubackend.lemu_backend.dto.ProdottoDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface OrdineService {

    void save(Ordine ordine);
    ProdottoDto getById(Long id);

    Collection<ProdottoDto> findAll();

    
}

