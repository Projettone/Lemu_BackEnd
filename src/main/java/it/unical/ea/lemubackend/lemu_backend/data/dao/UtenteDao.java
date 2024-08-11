package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteDao extends JpaRepository<Utente, Long>, JpaSpecificationExecutor<Utente> {

    Optional<Utente> findByCredenzialiEmail(String credenzialiEmail);

    @Transactional
    @Modifying
    @Query("UPDATE Utente u SET u.bannato = true WHERE u.credenziali.email = :email")
    void banUserByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Utente u SET u.bannato = false WHERE u.credenziali.email = :email")
    void unbanUserByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Utente u SET u.isAdmin = true WHERE u.credenziali.email = :email")
    void makeAdminByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Utente u SET u.isAdmin = false WHERE u.credenziali.email = :email")
    void revokeAdminByEmail(String email);


    @Transactional
    @Modifying
    @Query("UPDATE Utente u SET u.saldo = u.saldo + :amount WHERE u.credenziali.email = :email")
    void updateBalanceByEmail(String email, Double amount);

    List<Utente> findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCaseOrCredenzialiEmailContainingIgnoreCase(String name, String surname, String email);


}
