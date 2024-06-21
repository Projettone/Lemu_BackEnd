package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.dto.WishlistDto;
import it.unical.ea.lemubackend.lemu_backend.data.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<WishlistDto> createWishlist(@RequestBody WishlistDto wishlistDto) {
        return ResponseEntity.ok(wishlistService.createWishlist(wishlistDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WishlistDto> updateWishlist(@PathVariable Long id, @RequestBody WishlistDto wishlistDto) {
        return ResponseEntity.ok(wishlistService.updateWishlist(id, wishlistDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long id) {
        wishlistService.deleteWishlist(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishlistDto> getWishlistById(@PathVariable Long id) {
        return ResponseEntity.ok(wishlistService.getWishlistById(id));
    }

    @GetMapping
    public ResponseEntity<List<WishlistDto>> getAllWishlists() {
        return ResponseEntity.ok(wishlistService.getAllWishlists());
    }
}

