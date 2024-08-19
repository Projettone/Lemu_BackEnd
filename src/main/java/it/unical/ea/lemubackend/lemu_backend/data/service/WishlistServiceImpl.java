package it.unical.ea.lemubackend.lemu_backend.data.service;


import it.unical.ea.lemubackend.lemu_backend.data.dao.WishlistDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Wishlist;
import it.unical.ea.lemubackend.lemu_backend.dto.WishlistDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistDao wishlistDao;

    private final ModelMapper modelmapper;

    public WishlistServiceImpl(ModelMapper modelmapper) {
        this.modelmapper = modelmapper;
    }


    @Override
    public WishlistDto createWishlist(WishlistDto wishlistDto) {
        Wishlist wishlist = modelmapper.map(wishlistDto,Wishlist.class);
        wishlist = wishlistDao.save(wishlist);
        return convertToDto(wishlist);
    }

    @Override
    public WishlistDto updateWishlist(Long id, WishlistDto wishlistDto) {
        Wishlist wishlist = wishlistDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        wishlist = wishlistDao.save(wishlist);
        return convertToDto(wishlist);
    }

    @Override
    public void deleteWishlist(Long id) {
        if(wishlistDao.existsById(id)){
            wishlistDao.deleteById(id);
        }
    }

    @Override
    public WishlistDto getWishlistByUtenteId(Long utenteId) {
        Wishlist wishlist = wishlistDao.findByUtenteId(utenteId).orElseThrow(() -> new RuntimeException("Wishlist not found for user id: " + utenteId));
        return convertToDto(wishlist);

    }

    public List<WishlistDto> getAllWishlistByUtenteId(Long utenteId){
        List<Wishlist> wishlists = wishlistDao.findAllByUtenteId(utenteId);

        // Converti la lista di Wishlist in una lista di WishlistDto
        return wishlists.stream()
                .map(this::convertToDto)  // Usa il metodo convertToDto per ogni elemento
                .collect(Collectors.toList());
    }

    @Override
    public List<WishlistDto> getPublicWishlistsByUserId(Long utenteId) {
        List<Wishlist> wishlists = wishlistDao.findAllByUtenteIdAndTipo(utenteId, "pubblica");
        return wishlists.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private WishlistDto convertToDto(Wishlist wishlist) {
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setId(wishlist.getId());
        wishlistDto.setNome(wishlist.getNome());
        wishlistDto.setTipo(wishlist.getTipo());
        wishlistDto.setUtenteId(wishlist.getUtente().getId());
        // Converti altri campi se necessario
        return wishlistDto;
    }
}

