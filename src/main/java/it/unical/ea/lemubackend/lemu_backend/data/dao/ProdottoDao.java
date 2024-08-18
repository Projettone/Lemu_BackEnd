package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProdottoDao extends JpaRepository<Prodotto, Long> {
    @Query("SELECT p FROM Prodotto p WHERE p.nome LIKE %:keyword% OR p.descrizione LIKE %:keyword% OR p.categoria LIKE %:keyword%")
    List<Prodotto> searchByKeyword(@Param("keyword") String keyword);


    @Query("SELECT p FROM Prodotto p WHERE p.categoria = :categoria")
    List<Prodotto> findByCategoria(@Param("categoria") String categoria);

}
