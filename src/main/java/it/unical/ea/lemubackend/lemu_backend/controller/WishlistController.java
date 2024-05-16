package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.dto.WishlistDto;
import it.unical.ea.lemubackend.lemu_backend.data.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<WishlistDto> createWishlist(@RequestBody WishlistDto wishlistDto) {
        WishlistDto createdWishlist = wishlistService.saveWishlist(wishlistDto);
        return ResponseEntity.ok(createdWishlist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishlistDto> getWishlistById(@PathVariable Long id) {
        Optional<WishlistDto> wishlistDto = wishlistService.getWishlistById(id);
        return wishlistDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/utente/{utenteId}")
    public ResponseEntity<WishlistDto> getWishlistByUtenteId(@PathVariable Long utenteId) {
        WishlistDto wishlistDto = wishlistService.getWishlistByUtenteId(utenteId);
        return ResponseEntity.ok(wishlistDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long id) {
        wishlistService.deleteWishlist(id);
        return ResponseEntity.noContent().build();
    }
}
