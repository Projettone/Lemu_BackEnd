package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.dto.WishlistCondivisioneDto;
import it.unical.ea.lemubackend.lemu_backend.data.service.WishlistCondivisioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emailCondivisioni")
public class WishlistCondivisioneController {

    @Autowired
    private WishlistCondivisioneService wishlistCondivisioneService;

    @PostMapping
    public ResponseEntity<WishlistCondivisioneDto> createEmailCondivisione(@RequestBody WishlistCondivisioneDto wishlistCondivisioneDto) {
        return ResponseEntity.ok(wishlistCondivisioneService.createWishlistCondivisione(wishlistCondivisioneDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WishlistCondivisioneDto> updateWishlistCondivisione(@PathVariable Long id, @RequestBody WishlistCondivisioneDto wishlistCondivisioneDto) {
        return ResponseEntity.ok(wishlistCondivisioneService.updateWishlistCondivisione(id, wishlistCondivisioneDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlistCondivisione(@PathVariable Long id) {
        wishlistCondivisioneService.deleteWishlistCondivisione(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishlistCondivisioneDto> getWishlistCondivisioneById(@PathVariable Long id) {
        return ResponseEntity.ok(wishlistCondivisioneService.getWishlistCondivisioneById(id));
    }

    @GetMapping
    public ResponseEntity<List<WishlistCondivisioneDto>> getAllWishlistCondivisioni() {
        return ResponseEntity.ok(wishlistCondivisioneService.getAllWishlistCondivisioni());
    }
}
