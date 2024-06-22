package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.dto.WishlistDto;
import it.unical.ea.lemubackend.lemu_backend.data.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlists-api")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/add")
    public ResponseEntity<WishlistDto> createWishlist(@RequestBody WishlistDto wishlistDto) {
        return ResponseEntity.ok(wishlistService.createWishlist(wishlistDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WishlistDto> updateWishlist(@PathVariable Long id, @RequestBody WishlistDto wishlistDto) {
        return ResponseEntity.ok(wishlistService.updateWishlist(id, wishlistDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long id) {
        wishlistService.deleteWishlist(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<WishlistDto> getWishlistById(@PathVariable Long id) {
        return ResponseEntity.ok(wishlistService.getWishlistById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<WishlistDto>> getAllWishlists() {
        return ResponseEntity.ok(wishlistService.getAllWishlists());
    }
}

