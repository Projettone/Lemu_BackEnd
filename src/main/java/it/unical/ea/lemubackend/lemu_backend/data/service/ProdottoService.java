package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.dto.ProdottoDto;

import java.util.Collection;

public interface ProdottoService {

    boolean save(ProdottoDto prodottoDto, String jwt);
    Collection<ProdottoDto> findAll();
    ProdottoDto getById(Long id);
}
