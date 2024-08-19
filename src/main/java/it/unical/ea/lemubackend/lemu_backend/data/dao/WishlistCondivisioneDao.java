package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.WishlistCondivisione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistCondivisioneDao extends JpaRepository<WishlistCondivisione, Long> {
    List<WishlistCondivisione> findAllByWishlistId(Long wishlistId);

}

