package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistDao extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByUtenteId(Long utenteId);
    List<Wishlist> findAllByUtenteId(Long utenteId);
    List<Wishlist> findAllByUtenteIdAndTipo(Long utenteId, String tipo);


}

