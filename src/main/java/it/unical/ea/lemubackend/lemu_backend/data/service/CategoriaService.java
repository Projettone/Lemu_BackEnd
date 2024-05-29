package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.dto.CategoriaDto;

import java.util.List;
import java.util.Optional;
public interface CategoriaService {
    Optional<CategoriaDto> getCategoriaById(Long id);
    List<CategoriaDto> getAllCategorie();
}
