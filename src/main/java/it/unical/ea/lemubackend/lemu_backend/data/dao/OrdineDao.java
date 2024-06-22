package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Ordine;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdineDao extends JpaRepository<Ordine, Long> {

    List<Ordine> findOrdineByUtente_Id(Long id);

}
