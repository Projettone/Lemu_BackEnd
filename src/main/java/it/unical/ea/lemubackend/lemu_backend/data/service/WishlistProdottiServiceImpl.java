package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.ProdottoDao;
import it.unical.ea.lemubackend.lemu_backend.data.dao.WishlistDao;
import it.unical.ea.lemubackend.lemu_backend.data.dao.WishlistProdottiDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Prodotto;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Wishlist;
import it.unical.ea.lemubackend.lemu_backend.data.entities.WishlistProdotti;
import it.unical.ea.lemubackend.lemu_backend.dto.WishlistProdottiDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistProdottiServiceImpl implements WishlistProdottiService {

    @Autowired
    private WishlistProdottiDao wishlistProdottiDao;

    @Autowired
    private WishlistDao wishlistDao;

    @Autowired
    private ProdottoDao prodottoDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WishlistProdottiDto createWishlistProdotti(WishlistProdottiDto wishlistProdottiDto) {
        Wishlist wishlist = wishlistDao.findById(wishlistProdottiDto.getWishlistId())
                .orElseThrow(() -> new IllegalArgumentException("Wishlist non trovato con ID: " + wishlistProdottiDto.getWishlistId()));
        Prodotto prodotto = prodottoDao.findById(wishlistProdottiDto.getProdottoId())
                .orElseThrow(() -> new IllegalArgumentException("Prodotto non trovato con ID: " + wishlistProdottiDto.getProdottoId()));

        WishlistProdotti wishlistProdotti = modelMapper.map(wishlistProdottiDto, WishlistProdotti.class);
        wishlistProdotti.setWishlist(wishlist);
        wishlistProdotti.setProdotto(prodotto);

        wishlistProdotti = wishlistProdottiDao.save(wishlistProdotti);

        return convertToDto(wishlistProdotti);
    }

    @Override
    public WishlistProdottiDto updateWishlistProdotti(Long id, WishlistProdottiDto wishlistProdottiDto) {
        WishlistProdotti wishlistProdotti = wishlistProdottiDao.findById(id)
                .orElseThrow(() -> new RuntimeException("WishlistProdotti not found with ID: " + id));

        if (wishlistProdottiDto.getWishlistId() != null &&
                !wishlistProdottiDto.getWishlistId().equals(wishlistProdotti.getWishlist().getId())) {
            Wishlist wishlist = wishlistDao.findById(wishlistProdottiDto.getWishlistId())
                    .orElseThrow(() -> new RuntimeException("Wishlist not found with ID: " + wishlistProdottiDto.getWishlistId()));
            wishlistProdotti.setWishlist(wishlist);
        }

        if (wishlistProdottiDto.getProdottoId() != null &&
                !wishlistProdottiDto.getProdottoId().equals(wishlistProdotti.getProdotto().getId())) {
            Prodotto prodotto = prodottoDao.findById(wishlistProdottiDto.getProdottoId())
                    .orElseThrow(() -> new RuntimeException("Prodotto not found with ID: " + wishlistProdottiDto.getProdottoId()));
            wishlistProdotti.setProdotto(prodotto);
        }

        wishlistProdotti = wishlistProdottiDao.save(wishlistProdotti);

        return convertToDto(wishlistProdotti);
    }

    @Override
    public void deleteWishlistProdotti(Long id) {
        wishlistProdottiDao.deleteById(id);
    }

    @Override
    public WishlistProdottiDto getWishlistProdottiById(Long id) {
        WishlistProdotti wishlistProdotti = wishlistProdottiDao.findById(id)
                .orElseThrow(() -> new RuntimeException("WishlistProdotti not found"));
        return convertToDto(wishlistProdotti);
    }
    @Override
    public List<WishlistProdottiDto> getAllWishlistProdottiByWishlistId(Long wishlistId) {
        List<WishlistProdotti> wishlistProdottiList = wishlistProdottiDao.findAllByWishlist_Id(wishlistId);
        return wishlistProdottiList.stream()
                .map(wishlistProdotti -> modelMapper.map(wishlistProdotti, WishlistProdottiDto.class))
                .toList();
    }

    private WishlistProdottiDto convertToDto(WishlistProdotti wishlistProdotti) {
        WishlistProdottiDto wishlistProdottiDto = new WishlistProdottiDto();
        wishlistProdottiDto.setId(wishlistProdotti.getId());
        wishlistProdottiDto.setWishlistId(wishlistProdotti.getWishlist().getId());
        wishlistProdottiDto.setProdottoId(wishlistProdotti.getProdotto().getId());
        return wishlistProdottiDto;
    }

    /*
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

     */
}
