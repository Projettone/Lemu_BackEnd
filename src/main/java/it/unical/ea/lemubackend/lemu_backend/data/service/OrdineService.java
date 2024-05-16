package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Prodotto;
import it.unical.ea.lemubackend.lemu_backend.dto.ProdottoDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface OrdineService {

    void save(Prodotto prodotto); // Questo metodo viene utilizzato per salvare un prodotto nel sistema. Prende un oggetto prodotto come parametro e non restituisce alcun valore (void). Questo metodo è utilizzato per aggiungere nuovi articoli al sistema.

    ProdottoDto getById(Long id); //Questo metodo restituisce un articolo specifico identificato dal suo ID. Prende l'ID dell'articolo come parametro e restituisce un oggetto ProdottoDto che rappresenta l'articolo trovato.

    Collection<ProdottoDto> findAll(); //Questo metodo restituisce una collezione di tutti gli articoli presenti nel sistema. Restituisce una collezione di oggetti ProdottoDto.


    /*
   DA VEDERE
    ProdottoDto save(ProdottoDto prodottoDto, String jwt); //Questo metodo è simile al precedente, ma prende un oggetto ArticoloDto anziché un Articolo. Un DTO (Data Transfer Object) è spesso utilizzato per trasferire dati tra i componenti del sistema. Restituisce un oggetto ArticoloDto che rappresenta l'articolo salvato. Il parametro jwt sembra essere un token di autenticazione.


    CompletableFuture<HttpStatus> delete(Long id, String jwt); // Questo metodo è utilizzato per eliminare un articolo dal sistema. Prende l'ID dell'articolo da eliminare e un token di autenticazione jwt. Restituisce un oggetto CompletableFuture che può essere utilizzato per eseguire operazioni asincrone. L'oggetto HttpStatus restituito indica lo stato dell'operazione di eliminazione.
  */
}

