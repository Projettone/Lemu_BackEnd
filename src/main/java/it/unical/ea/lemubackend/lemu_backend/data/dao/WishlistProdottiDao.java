package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.WishlistProdotti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistProdottiDao extends JpaRepository<WishlistProdotti, Long> {
    List<WishlistProdotti> findAllByWishlist_Id(Long wishlist_id);

}

