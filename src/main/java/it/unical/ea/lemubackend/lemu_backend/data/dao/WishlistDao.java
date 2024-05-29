package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface WishlistDao extends JpaRepository<Wishlist, Long>{
    Wishlist findByUtenteId(Long utenteId); //da cambiare
}
