package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.dto.WishlistDto;

import java.util.Optional;

public interface WishlistService {
    WishlistDto saveWishlist(WishlistDto wishlistDto);
    Optional<WishlistDto> getWishlistById(Long id);
    WishlistDto getWishlistByUtenteId(Long utenteId);
    void deleteWishlist(Long id);
}
