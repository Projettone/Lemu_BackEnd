package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.WishlistCondivisioneDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Wishlist;
import it.unical.ea.lemubackend.lemu_backend.dto.WishlistCondivisioneDto;
import it.unical.ea.lemubackend.lemu_backend.data.entities.WishlistCondivisione;
import it.unical.ea.lemubackend.lemu_backend.data.service.WishlistCondivisioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistCondivisioneServiceImpl implements WishlistCondivisioneService {

    @Autowired
    private WishlistCondivisioneDao wishlistCondivisioneDao;

    @Override
    public WishlistCondivisioneDto createWishlistCondivisione(WishlistCondivisioneDto wishlistCondivisioneDto) {
        WishlistCondivisione wishlistCondivisione = new WishlistCondivisione();
        wishlistCondivisione.setEmail(wishlistCondivisioneDto.getEmail());
        //wishlistCondivisione.setWishlist(new Wishlist(wishlistCondivisioneDto.getWishlistId()));
        wishlistCondivisioneDao.save(wishlistCondivisione);
        wishlistCondivisioneDto.setId(wishlistCondivisione.getId());
        return wishlistCondivisioneDto;
    }

    @Override
    public WishlistCondivisioneDto updateWishlistCondivisione(Long id, WishlistCondivisioneDto wishlistCondivisioneDto) {
        //WishlistCondivisione wishlistCondivisione = wishlistCondivisioneDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("WishlistCondivisione not found"));
        //wishlistCondivisione.setEmail(wishlistCondivisioneDto.getEmail());
        //wishlistCondivisione.setWishlist(new Wishlist(wishlistCondivisioneDto.getWishlistId()));
        //wishlistCondivisioneDao.save(wishlistCondivisione);
        return wishlistCondivisioneDto;
    }

    @Override
    public void deleteWishlistCondivisione(Long id) {
        wishlistCondivisioneDao.deleteById(id);
    }

    @Override
    public WishlistCondivisioneDto getWishlistCondivisioneById(Long id) {
        //WishlistCondivisione wishlistCondivisione = wishlistCondivisioneDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("WishlistCondivisione not found"));
        WishlistCondivisioneDto wishlistCondivisioneDto = new WishlistCondivisioneDto();
        //wishlistCondivisioneDto.setId(wishlistCondivisione.getId());
        //wishlistCondivisioneDto.setEmail(wishlistCondivisione.getEmail());
        //wishlistCondivisioneDto.setWishlistId(wishlistCondivisione.getWishlist().getId());
        return wishlistCondivisioneDto;
    }

    @Override
    public List<WishlistCondivisioneDto> getAllWishlistCondivisioni() {
        return wishlistCondivisioneDao.findAll().stream()
                .map(wishlistCondivisione -> {
                    WishlistCondivisioneDto wishlistCondivisioneDto = new WishlistCondivisioneDto();
                    wishlistCondivisioneDto.setId(wishlistCondivisione.getId());
                    wishlistCondivisioneDto.setEmail(wishlistCondivisione.getEmail());
                    wishlistCondivisioneDto.setWishlistId(wishlistCondivisione.getWishlist().getId());
                    return wishlistCondivisioneDto;
                })
                .collect(Collectors.toList());
    }
}


