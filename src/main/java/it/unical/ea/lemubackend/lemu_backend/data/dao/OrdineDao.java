package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdineDao extends JpaRepository<Ordine, Long> {

    @Query("SELECT o FROM Ordine o WHERE o.utente.id = :utenteId")
    List<Ordine> findOrdiniByUtenteId(@Param("utenteId") Long utenteId);
}
