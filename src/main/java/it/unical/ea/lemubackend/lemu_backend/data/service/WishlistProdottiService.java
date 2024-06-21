package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.dto.WishlistProdottiDto;
import java.util.List;

public interface WishlistProdottiService {
    WishlistProdottiDto createWishlistProdotti(WishlistProdottiDto wishlistProdottiDto);
    WishlistProdottiDto updateWishlistProdotti(Long id, WishlistProdottiDto wishlistProdottiDto);
    void deleteWishlistProdotti(Long id);
    WishlistProdottiDto getWishlistProdottiById(Long id);
    List<WishlistProdottiDto> getAllWishlistProdotti();
}


