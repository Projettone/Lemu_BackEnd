package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.dto.OrdineDto;

import java.util.Collection;

public interface OrdineService {

    OrdineDto save(OrdineDto ordine, String jwt);
    OrdineDto getById(Long id);
    Collection<OrdineDto> findOrderbyUser(Long jwt);

    
}

