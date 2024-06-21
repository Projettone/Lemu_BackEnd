package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.dto.WishlistProdottiDto;
import it.unical.ea.lemubackend.lemu_backend.data.service.WishlistProdottiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlistProdotti")
public class WishlistProdottiController {

    @Autowired
    private WishlistProdottiService wishlistProdottiService;

    @PostMapping
    public ResponseEntity<WishlistProdottiDto> createWishlistProdotti(@RequestBody WishlistProdottiDto wishlistProdottiDto) {
        return ResponseEntity.ok(wishlistProdottiService.createWishlistProdotti(wishlistProdottiDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WishlistProdottiDto> updateWishlistProdotti(@PathVariable Long id, @RequestBody WishlistProdottiDto wishlistProdottiDto) {
        return ResponseEntity.ok(wishlistProdottiService.updateWishlistProdotti(id, wishlistProdottiDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlistProdotti(@PathVariable Long id) {
        wishlistProdottiService.deleteWishlistProdotti(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishlistProdottiDto> getWishlistProdottiById(@PathVariable Long id) {
        return ResponseEntity.ok(wishlistProdottiService.getWishlistProdottiById(id));
    }

    @GetMapping
    public ResponseEntity<List<WishlistProdottiDto>> getAllWishlistProdotti() {
        return ResponseEntity.ok(wishlistProdottiService.getAllWishlistProdotti());
    }
}

