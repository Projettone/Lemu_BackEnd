package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.data.service.WishlistProdottiService;
import it.unical.ea.lemubackend.lemu_backend.data.service.WishlistService;
import it.unical.ea.lemubackend.lemu_backend.dto.WishlistDto;
import it.unical.ea.lemubackend.lemu_backend.dto.WishlistProdottiDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist-api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private WishlistProdottiService wishlistProdottiService;

    //Wishlist

    @PostMapping("/add")
    public ResponseEntity<WishlistDto> createWishlist(@RequestBody WishlistDto wishlistDto) {
        WishlistDto createdWishlist = wishlistService.createWishlist(wishlistDto);
        return ResponseEntity.ok(createdWishlist);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WishlistDto> updateWishlist(@PathVariable Long id, @RequestBody WishlistDto wishlistDto) {
        WishlistDto updatedWishlist = wishlistService.updateWishlist(id, wishlistDto);
        return ResponseEntity.ok(updatedWishlist);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long id) {
        wishlistService.deleteWishlist(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getByUtente/{utenteId}")
    public ResponseEntity<WishlistDto> getWishlistByUtenteId(@PathVariable Long utenteId) {
        WishlistDto wishlistDto = wishlistService.getWishlistByUtenteId(utenteId);
        return ResponseEntity.ok(wishlistDto);
    }

    @GetMapping("/utente/{utenteId}")
    public ResponseEntity<List<WishlistDto>> getAllWishlistByUtenteId(@PathVariable Long utenteId) {
        List<WishlistDto> wishlists = wishlistService.getAllWishlistByUtenteId(utenteId);
        return ResponseEntity.ok(wishlists);
    }


    //WishlistProdotti

    @PostMapping("/prodotti/add")
    public ResponseEntity<WishlistProdottiDto> createWishlistProdotti(@RequestBody WishlistProdottiDto wishlistProdottiDto) {
        WishlistProdottiDto createdWishlistProdotti = wishlistProdottiService.createWishlistProdotti(wishlistProdottiDto);
        return ResponseEntity.ok(createdWishlistProdotti);
    }

    @PutMapping("/prodotti/update/{id}")
    public ResponseEntity<WishlistProdottiDto> updateWishlistProdotti(@PathVariable Long id, @RequestBody WishlistProdottiDto wishlistProdottiDto) {
        WishlistProdottiDto updatedWishlistProdotti = wishlistProdottiService.updateWishlistProdotti(id, wishlistProdottiDto);
        return ResponseEntity.ok(updatedWishlistProdotti);
    }

    @DeleteMapping("/prodotti/delete/{id}")
    public ResponseEntity<Void> deleteWishlistProdotti(@PathVariable Long id) {
        wishlistProdottiService.deleteWishlistProdotti(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("prodotti/get/{id}")
    public ResponseEntity<WishlistProdottiDto> getWishlistProdottiById(@PathVariable Long id) {
        WishlistProdottiDto wishlistProdottiDto =wishlistProdottiService.getWishlistProdottiById(id);
        return ResponseEntity.ok(wishlistProdottiDto);
    }

    @GetMapping("/prodotti/get/{wishlistid}/all")
    public ResponseEntity<List<WishlistProdottiDto>> getAllWishlistProdotti(@PathVariable Long wishlistid) {
        List<WishlistProdottiDto> wishlistProdottiList = wishlistProdottiService.getAllWishlistProdottiByWishlistId(wishlistid);
        return ResponseEntity.ok(wishlistProdottiList);
    }

    @GetMapping("/public/{utenteId}")
    public ResponseEntity<List<WishlistDto>> getPublicWishlistsByUserId(@PathVariable Long utenteId) {
        List<WishlistDto> publicWishlists = wishlistService.getPublicWishlistsByUserId(utenteId);
        return ResponseEntity.ok(publicWishlists);
    }
}

