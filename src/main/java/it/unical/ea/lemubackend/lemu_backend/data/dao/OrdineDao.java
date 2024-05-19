package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdineDao extends JpaRepository<Ordine, Long> {

}
