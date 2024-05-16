package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Prodotto;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Recensione;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecensioneDao extends JpaRepository<Recensione, Long> {

    List<Recensione> findAllByProdotto(Prodotto prodotto);

    List<Recensione> findAllByAutore(Utente autore);

    List<Recensione> findAllByRating(Float rating);

}
