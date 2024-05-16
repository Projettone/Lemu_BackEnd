package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteDao extends JpaRepository<Utente, Long>, JpaSpecificationExecutor<Utente> {

    List<Utente> getUtenteById(Long id);

    List<Utente> getUtenteByBannato(Boolean bannato);

    List<Utente> getUtenteByAdmin(Boolean admin);
}
