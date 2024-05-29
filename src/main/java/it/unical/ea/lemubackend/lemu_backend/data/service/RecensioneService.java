package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Recensione;
import it.unical.ea.lemubackend.lemu_backend.dto.RecensioneDto;

import java.util.Collection;
import java.util.List;

public interface RecensioneService {

    void save(Recensione recensione);

    Collection<RecensioneDto> findAll();

    RecensioneDto getById(Long id);
}
