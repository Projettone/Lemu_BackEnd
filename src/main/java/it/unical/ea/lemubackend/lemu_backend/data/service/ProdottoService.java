package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Prodotto;
import it.unical.ea.lemubackend.lemu_backend.dto.ProdottoDto;

import java.util.Collection;

public interface ProdottoService {

    ProdottoDto save(ProdottoDto prodottoDto, String jwt);
    Collection<ProdottoDto> findAll();
    ProdottoDto getById(Long id);
    void save(ProdottoDto prodotto);
}
