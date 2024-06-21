package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.WishlistProdottiDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Prodotto;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Wishlist;
import it.unical.ea.lemubackend.lemu_backend.dto.WishlistProdottiDto;
import it.unical.ea.lemubackend.lemu_backend.data.entities.WishlistProdotti;
import it.unical.ea.lemubackend.lemu_backend.data.service.WishlistProdottiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistProdottiServiceImpl implements WishlistProdottiService {

    @Autowired
    private WishlistProdottiDao wishlistProdottiDao;

    @Override
    public WishlistProdottiDto createWishlistProdotti(WishlistProdottiDto wishlistProdottiDto) {
        WishlistProdotti wishlistProdotti = new WishlistProdotti();
        //wishlistProdotti.setWishlist(new Wishlist(wishlistProdottiDto.getWishlistId()));
        //wishlistProdotti.setProdotto(new Prodotto(wishlistProdottiDto.getProdottoId()));
        wishlistProdottiDao.save(wishlistProdotti);
        wishlistProdottiDto.setId(wishlistProdotti.getId());
        return wishlistProdottiDto;
    }

    @Override
    public WishlistProdottiDto updateWishlistProdotti(Long id, WishlistProdottiDto wishlistProdottiDto) {
        //WishlistProdotti wishlistProdotti = wishlistProdottiDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("WishlistProdotti not found"));
        //wishlistProdotti.setWishlist(new Wishlist(wishlistProdottiDto.getWishlistId()));
        //wishlistProdotti.setProdotto(new Prodotto(wishlistProdottiDto.getProdottoId()));
        //wishlistProdottiDao.save(wishlistProdotti);
        return wishlistProdottiDto;
    }

    @Override
    public void deleteWishlistProdotti(Long id) {
        wishlistProdottiDao.deleteById(id);
    }

    @Override
    public WishlistProdottiDto getWishlistProdottiById(Long id) {
        //WishlistProdotti wishlistProdotti = wishlistProdottiDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("WishlistProdotti not found"));
        WishlistProdottiDto wishlistProdottiDto = new WishlistProdottiDto();
        //wishlistProdottiDto.setId(wishlistProdotti.getId());
        //wishlistProdottiDto.setWishlistId(wishlistProdotti.getWishlist().getId());
        //wishlistProdottiDto.setProdottoId(wishlistProdotti.getProdotto().getId());
        return wishlistProdottiDto;
    }

    @Override
    public List<WishlistProdottiDto> getAllWishlistProdotti() {
        return wishlistProdottiDao.findAll().stream()
                .map(wishlistProdotti -> {
                    WishlistProdottiDto wishlistProdottiDto = new WishlistProdottiDto();
                    wishlistProdottiDto.setId(wishlistProdotti.getId());
                    wishlistProdottiDto.setWishlistId(wishlistProdotti.getWishlist().getId());
                    wishlistProdottiDto.setProdottoId(wishlistProdotti.getProdotto().getId());
                    return wishlistProdottiDto;
                })
                .collect(Collectors.toList());
    }
}
