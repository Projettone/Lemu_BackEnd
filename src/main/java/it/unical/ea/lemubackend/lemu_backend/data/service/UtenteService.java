package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteDto;

public interface UtenteService {

    public void save(Utente utente);
    UtenteDto getById(Long id);

    UtenteDto getByCEmail(String email);

}
