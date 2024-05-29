package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.WishlistDao;
import it.unical.ea.lemubackend.lemu_backend.dto.WishlistDto;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Wishlist;
import it.unical.ea.lemubackend.lemu_backend.data.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
// mettere le eccezioni
@Service
public class WishlistServiceImpl implements WishlistService{
    @Autowired
    private WishlistDao wishlistDao;

    @Override
    public WishlistDto saveWishlist(WishlistDto wishlistDto) {
        Wishlist wishlist = convertToEntity(wishlistDto);
        Wishlist savedWishlist = wishlistDao.save(wishlist);
        return convertToDto(savedWishlist);
    }

    @Override
    public Optional<WishlistDto> getWishlistById(Long id) {
        Optional<Wishlist> wishlist = wishlistDao.findById(id);
        return wishlist.map(this::convertToDto);
    }

    @Override
    public WishlistDto getWishlistByUtenteId(Long utenteId) {
        Wishlist wishlist = wishlistDao.findByUtenteId(utenteId);
        return convertToDto(wishlist);
    }

    @Override
    public void deleteWishlist(Long id) {
        wishlistDao.deleteById(id);
    }

    // Metodo di conversione da DTO a Entità
    private Wishlist convertToEntity(WishlistDto wishlistDto) {
        Wishlist wishlist = new Wishlist();
        wishlist.setId(wishlistDto.getId());
        //wishlist.setProdotti(wishlistDto.getProdotti());
        return wishlist;
    }

    // Metodo di conversione da Entità a DTO
    private WishlistDto convertToDto(Wishlist wishlist) {
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setId(wishlist.getId());
        wishlistDto.setUtenteId(wishlist.getUtente().getId());
        //wishlistDto.setProdotti(wishlist.getProdotti());
        return wishlistDto;
    }
}
