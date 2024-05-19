package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.OrdineDao;
import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Ordine;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Prodotto;
import it.unical.ea.lemubackend.lemu_backend.dto.ProdottoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
@RequiredArgsConstructor
public class OrdineServiceImpl implements OrdineService {

    private final OrdineDao ordineDao;
    private final OrdineDao articoloDao;
    private final UtenteDao utenteDao;
    @Override
    public void save(Ordine ordine) {
        ordineDao.save(ordine);
    }

    @Override
    public ProdottoDto getById(Long id) {
        return null;
    }

    @Override
    public Collection<ProdottoDto> findAll() {
        return null;
    }
}
