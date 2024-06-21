package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.dto.WishlistDto;
import java.util.List;

public interface WishlistService {
    WishlistDto createWishlist(WishlistDto wishlistDto);
    WishlistDto updateWishlist(Long id, WishlistDto wishlistDto);
    void deleteWishlist(Long id);
    WishlistDto getWishlistById(Long id);
    List<WishlistDto> getAllWishlists();
}

