package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UtenteDao extends JpaRepository<Utente, Long> {

    List<Utente> getUtenteById(Long id);

    List<Utente> getUtenteByBannato(Boolean bannato);

    List<Utente> getUtenteByAdmin(Boolean admin);

}
