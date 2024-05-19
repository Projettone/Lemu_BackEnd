package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdottoDao extends JpaRepository<Prodotto, Long> {
}
