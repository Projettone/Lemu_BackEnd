package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.WishlistDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.WishlistDto;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Wishlist;
import it.unical.ea.lemubackend.lemu_backend.data.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistDao wishlistDao;

    @Override
    public WishlistDto createWishlist(WishlistDto wishlistDto) {
        Wishlist wishlist = new Wishlist();
        wishlist.setNome(wishlistDto.getNome());
        wishlist.setTipo(wishlistDto.getTipo());
        //wishlist.setUtente(new Utente(wishlistDto.getUtenteId()));
        wishlistDao.save(wishlist);
        wishlistDto.setId(wishlist.getId());
        return wishlistDto;
    }

    @Override
    public WishlistDto updateWishlist(Long id, WishlistDto wishlistDto) {
        //Wishlist wishlist = wishlistDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        //wishlist.setNome(wishlistDto.getNome());
        //wishlist.setTipo(wishlistDto.getTipo());
        //wishlist.setUtente(new Utente(wishlistDto.getUtenteId()));
        //wishlistDao.save(wishlist);
        return wishlistDto;
    }

    @Override
    public void deleteWishlist(Long id) {
        wishlistDao.deleteById(id);
    }

    @Override
    public WishlistDto getWishlistById(Long id) {
        //Wishlist wishlist = wishlistDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        WishlistDto wishlistDto = new WishlistDto();
        //wishlistDto.setId(wishlist.getId());
        //wishlistDto.setNome(wishlist.getNome());
        //wishlistDto.setTipo(wishlist.getTipo());
        //wishlistDto.setUtenteId(wishlist.getUtente().getId());
        return wishlistDto;
    }

    @Override
    public List<WishlistDto> getAllWishlists() {
        return wishlistDao.findAll().stream()
                .map(wishlist -> {
                    WishlistDto wishlistDto = new WishlistDto();
                    wishlistDto.setId(wishlist.getId());
                    wishlistDto.setNome(wishlist.getNome());
                    wishlistDto.setTipo(wishlist.getTipo());
                    wishlistDto.setUtenteId(wishlist.getUtente().getId());
                    return wishlistDto;
                })
                .collect(Collectors.toList());
    }
}

