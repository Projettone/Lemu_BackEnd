package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.dto.WishlistCondivisioneDto;
import it.unical.ea.lemubackend.lemu_backend.data.service.WishlistCondivisioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("wishlistcondivisione-api")
public class WishlistCondivisioneController {

    @Autowired
    private WishlistCondivisioneService wishlistCondivisioneService;

    @PostMapping("/add")
    public ResponseEntity<WishlistCondivisioneDto> createEmailCondivisione(@RequestBody WishlistCondivisioneDto wishlistCondivisioneDto) {
        return ResponseEntity.ok(wishlistCondivisioneService.createWishlistCondivisione(wishlistCondivisioneDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WishlistCondivisioneDto> updateWishlistCondivisione(@PathVariable Long id, @RequestBody WishlistCondivisioneDto wishlistCondivisioneDto) {
        return ResponseEntity.ok(wishlistCondivisioneService.updateWishlistCondivisione(id, wishlistCondivisioneDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWishlistCondivisione(@PathVariable Long id) {
        wishlistCondivisioneService.deleteWishlistCondivisione(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<WishlistCondivisioneDto> getWishlistCondivisioneById(@PathVariable Long id) {
        return ResponseEntity.ok(wishlistCondivisioneService.getWishlistCondivisioneById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<WishlistCondivisioneDto>> getAllWishlistCondivisioni() {
        return ResponseEntity.ok(wishlistCondivisioneService.getAllWishlistCondivisioni());
    }
}
