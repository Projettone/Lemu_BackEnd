package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.ProdottoDao;
import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Prodotto;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.ProdottoDto;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdottoServiceImpl implements  ProdottoService {

    private final ProdottoDao prodottoDao;
    private final UtenteDao utenteDao;
    private final ModelMapper modelMapper;
    private final UtenteService utenteService;




    @Override
    public ProdottoDto save(ProdottoDto prodottoDto, String encodedJwt) {
        /*
        try {
            // Decodifica del JWT
            String jwt = new String(Base64.getDecoder().decode(encodedJwt));

            // Trova l'utente venditore
            Utente venditore = utenteDao.findById(prodottoDto.getUtente().getId()).orElse(null);

            // Verifica il token e confronta l'email
            String username = TokenStore.getInstance().getUser(jwt);
            if (username != null && venditore != null && username.equals(venditore.getCredenziali().getEmail())) {
                // Mappa ProdottoDto a Prodotto
                Prodotto prodotto = modelMapper.map(prodottoDto, Prodotto.class);
                prodotto.setUtente(venditore); // Associa l'utente al prodotto

                // Salva il prodotto nel database
                prodotto = prodottoDao.save(prodotto);

                // Mappa il Prodotto salvato di nuovo a ProdottoDto e restituiscilo
                return modelMapper.map(prodotto, ProdottoDto.class);
            } else {
                // Se la verifica fallisce, restituisce null
                return null;
            }
        } catch (Exception e) {
            // Gestione dell'eccezione
            e.printStackTrace(); // Puoi gestire le eccezioni in modo più appropriato a seconda del tuo caso d'uso
            return null;
        }

         */
        return  null;
    }


    @Override
    public Collection<ProdottoDto> findAll(int start, int end) {
        List<Prodotto> prodotti = prodottoDao.findAll();

        // Log per vedere quanti prodotti sono stati trovati
        System.out.println("Numero totale di prodotti: " + prodotti.size());

        // Log per vedere quali prodotti sono stati trovati
        prodotti.forEach(prodotto -> System.out.println("Prodotto trovato: ID=" + prodotto.getId() + ", Nome=" + prodotto.getNome()));

        // Verifica che gli indici start ed end siano validi
        if (start < 1 || end < start) {
            throw new IllegalArgumentException("Indici non validi: start deve essere >= 1 e end deve essere >= start");
        }

        // Se la dimensione della lista è inferiore all'indice di inizio, restituisci una lista vuota
        if (prodotti.size() < start) {
            return new ArrayList<>();
        }

        List<ProdottoDto> result = prodotti.stream()
                .skip(start - 1) // Salta i primi 'start - 1' elementi
                .limit(end - start + 1) // Limita il numero di risultati a 'end - start + 1'
                .map(this::convertToDto)
                .collect(Collectors.toList());

        // Log per vedere quanti prodotti sono stati selezionati dopo skip e limit
        System.out.println("Numero di prodotti dopo skip e limit: " + result.size());

        // Log per vedere quali prodotti vengono restituiti
        result.forEach(prodottoDto -> System.out.println("Prodotto restituito: ID=" + prodottoDto.getId() + ", Nome=" + prodottoDto.getNome()));

        return result;
    }

    private ProdottoDto convertToDto(Prodotto prodotto) {
        return modelMapper.map(prodotto, ProdottoDto.class);
    }


    @Override
    public ProdottoDto getById(Long id) {
        Prodotto prodotto = prodottoDao.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Non esiste un prodotto con id: [%s]", id)));
        return modelMapper.map(prodotto, ProdottoDto.class);
    }

    @Override
    public void save(ProdottoDto prodottoDto) {
        Prodotto p = modelMapper.map(prodottoDto, Prodotto.class);
        p.setImmagineBase64(prodottoDto.getImmagineProdotto());
        UtenteDto utenteDto = utenteService.getById(prodottoDto.getIdutente());
        Utente utente =  modelMapper.map(utenteDto, Utente.class);
        p.setUtente(utente);
        prodottoDao.save(p);
    }



    public List<ProdottoDto> searchProdotti(String keyword) {
        // Recupera la lista di prodotti dalla ricerca
        List<Prodotto> prodotti = prodottoDao.searchByKeyword(keyword);

        // Mappa ciascun prodotto a ProdottoDto
        return prodotti.stream()
                .map(prodotto -> modelMapper.map(prodotto, ProdottoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProdottoDto> getProdottiByCategoria(String categoria) {
        List<Prodotto> prodotti = prodottoDao.findByCategoria(categoria);

        return prodotti.stream()
                .map(prodotto -> modelMapper.map(prodotto, ProdottoDto.class))
                .collect(Collectors.toList());
    }


}
