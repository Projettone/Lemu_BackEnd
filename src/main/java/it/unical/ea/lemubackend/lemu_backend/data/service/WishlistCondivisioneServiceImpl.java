package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.WishlistCondivisioneDao;
import it.unical.ea.lemubackend.lemu_backend.data.dao.WishlistDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Wishlist;
import it.unical.ea.lemubackend.lemu_backend.data.entities.WishlistCondivisione;
import it.unical.ea.lemubackend.lemu_backend.dto.WishlistCondivisioneDto;
import it.unical.ea.lemubackend.lemu_backend.dto.WishlistDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistCondivisioneServiceImpl implements WishlistCondivisioneService {

    @Autowired
    private WishlistCondivisioneDao wishlistCondivisioneDao;

    @Autowired
    private WishlistDao wishlistDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WishlistCondivisioneDto createWishlistCondivisione(WishlistCondivisioneDto wishlistCondivisioneDto) {
        Wishlist wishlist = wishlistDao.findById(wishlistCondivisioneDto.getWishlistId())
                .orElseThrow(() -> new IllegalArgumentException("Wishlist non trovato con ID: " + wishlistCondivisioneDto.getWishlistId()));

        WishlistCondivisione wishlistCondivisione = modelMapper.map(wishlistCondivisioneDto, WishlistCondivisione.class);
        wishlistCondivisione.setWishlist(wishlist);

        wishlistCondivisione = wishlistCondivisioneDao.save(wishlistCondivisione);

        return convertToDto(wishlistCondivisione);
    }

    @Override
    public WishlistCondivisioneDto updateWishlistCondivisione(Long id, WishlistCondivisioneDto wishlistCondivisioneDto) {
        WishlistCondivisione wishlistCondivisione = wishlistCondivisioneDao.findById(id)
                .orElseThrow(() -> new RuntimeException("WishlistCondivisione non trovato con ID: " + id));

        if (wishlistCondivisioneDto.getWishlistId() != null &&
                !wishlistCondivisioneDto.getWishlistId().equals(wishlistCondivisione.getWishlist().getId())) {
            Wishlist wishlist = wishlistDao.findById(wishlistCondivisioneDto.getWishlistId())
                    .orElseThrow(() -> new RuntimeException("Wishlist non trovato con ID: " + wishlistCondivisioneDto.getWishlistId()));
            wishlistCondivisione.setWishlist(wishlist);
        }

        wishlistCondivisione.setEmail(wishlistCondivisioneDto.getEmail());

        wishlistCondivisione = wishlistCondivisioneDao.save(wishlistCondivisione);

        return convertToDto(wishlistCondivisione);
    }

    @Override
    public void deleteWishlistCondivisione(Long id) {
        wishlistCondivisioneDao.deleteById(id);
    }

    @Override
    public WishlistCondivisioneDto getWishlistCondivisioneById(Long id) {
        WishlistCondivisione wishlistCondivisione = wishlistCondivisioneDao.findById(id)
                .orElseThrow(() -> new RuntimeException("WishlistCondivisione non trovato con ID: " + id));
        return convertToDto(wishlistCondivisione);
    }

    @Override
    public List<WishlistCondivisioneDto> getAllWishlistCondivisioni() {
        return wishlistCondivisioneDao.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WishlistCondivisioneDto> getAllWishlistCondivisioniByWishlistId(Long wishlistId) {
        List<WishlistCondivisione> wishlistCondivisioneList = wishlistCondivisioneDao.findAllByWishlistId(wishlistId);
        return wishlistCondivisioneList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WishlistDto> getWishlistsCondiviseConEmail(Long utenteId, String email) {
        // Step 1: Trova tutte le wishlist di tipo "condivisa" per l'utente
        List<Wishlist> wishlistsCondivise = wishlistDao.findAllByUtenteIdAndTipo(utenteId, "condivisa");

        // Step 2: Crea una lista per conservare le wishlist condivise con l'email fornita
        List<WishlistDto> wishlistsCondiviseConEmail = new ArrayList<>();

        // Step 3: Per ogni wishlist, controlla se è condivisa con l'email data
        for (Wishlist wishlist : wishlistsCondivise) {
            List<WishlistCondivisione> condivisioni = wishlistCondivisioneDao.findAllByWishlistId(wishlist.getId());

            for (WishlistCondivisione condivisione : condivisioni) {
                if (condivisione.getEmail().equals(email)) {
                    wishlistsCondiviseConEmail.add(convertToWishlistDto(wishlist));
                    break; // Uscire dal loop interno se l'email è trovata
                }
            }
        }

        // Step 4: Restituisci la lista delle wishlist condivise con l'email data
        return wishlistsCondiviseConEmail;
    }

    private WishlistDto convertToWishlistDto(Wishlist wishlist) {
        return modelMapper.map(wishlist, WishlistDto.class);
    }





    private WishlistCondivisioneDto convertToDto(WishlistCondivisione wishlistCondivisione) {
        WishlistCondivisioneDto wishlistCondivisioneDto = new WishlistCondivisioneDto();
        wishlistCondivisioneDto.setId(wishlistCondivisione.getId());
        wishlistCondivisioneDto.setEmail(wishlistCondivisione.getEmail());
        wishlistCondivisioneDto.setWishlistId(wishlistCondivisione.getWishlist().getId());
        return wishlistCondivisioneDto;
    }
}
