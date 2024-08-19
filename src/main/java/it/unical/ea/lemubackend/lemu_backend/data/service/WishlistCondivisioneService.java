package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.dto.WishlistCondivisioneDto;
import it.unical.ea.lemubackend.lemu_backend.dto.WishlistDto;

import java.util.List;

public interface WishlistCondivisioneService {
    WishlistCondivisioneDto createWishlistCondivisione(WishlistCondivisioneDto wishlistCondivisioneDto);
    WishlistCondivisioneDto updateWishlistCondivisione(Long id, WishlistCondivisioneDto wishlistCondivisioneDto);
    void deleteWishlistCondivisione(Long id);
    WishlistCondivisioneDto getWishlistCondivisioneById(Long id);
    List<WishlistCondivisioneDto> getAllWishlistCondivisioni();
    List<WishlistCondivisioneDto> getAllWishlistCondivisioniByWishlistId(Long wishlistId);
    List<WishlistDto> getWishlistsCondiviseConEmail(Long utenteId, String email);
}


