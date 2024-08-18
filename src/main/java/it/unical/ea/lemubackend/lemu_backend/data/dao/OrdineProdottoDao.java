package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.OrdineProdotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdineProdottoDao extends JpaRepository<OrdineProdotto, Long> {


    @Query("SELECT op FROM OrdineProdotto op WHERE op.ordine.id = :ordineId ORDER BY op.id")
    List<OrdineProdotto> findDettagliOrdineByOrdineId(@Param("ordineId") Long ordineId);
}
