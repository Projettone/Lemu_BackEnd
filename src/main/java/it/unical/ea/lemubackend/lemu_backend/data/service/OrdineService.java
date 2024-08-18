package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.dto.OrdineDto;
import it.unical.ea.lemubackend.lemu_backend.dto.OrdineProdottoDto;

import java.util.Collection;

public interface OrdineService {

    void save(OrdineDto ordine);
    OrdineDto getById(Long id);
    Collection<OrdineDto> findOrderbyUser(Long idUser);
    Collection<OrdineDto> findAllOrders();
    Collection<OrdineProdottoDto> getDettagliOrdineByIdOrdine(Long id);
    //Collection<OrdineDto> findOrderByUserAndDate(Long userId, String dateFilter);


}

