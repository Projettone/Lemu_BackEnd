package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.dto.ProdottoDto;

import java.util.Collection;
import java.util.List;

public interface ProdottoService {

    ProdottoDto save(ProdottoDto prodottoDto, String jwt);
    Collection<ProdottoDto> findAll(int start, int end);
    ProdottoDto getById(Long id);
    void save(ProdottoDto prodotto);
    List<ProdottoDto> searchProdotti(String keyword);
    List<ProdottoDto> getProdottiByCategoria(String categoria);
    List<ProdottoDto> getByUserId(Long id);
    void deleteById(Long id);
}
