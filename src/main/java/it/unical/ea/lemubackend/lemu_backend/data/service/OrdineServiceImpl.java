package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.OrdineDao;
import it.unical.ea.lemubackend.lemu_backend.data.dao.OrdineProdottoDao;
import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Ordine;
import it.unical.ea.lemubackend.lemu_backend.data.entities.OrdineProdotto;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.OrdineDto;
import it.unical.ea.lemubackend.lemu_backend.dto.OrdineProdottoDto;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdineServiceImpl implements OrdineService {

    private final OrdineDao ordineDao;
    private final OrdineProdottoDao ordineProdottoDao;
    private final ModelMapper modelMapper;
    private final UtenteDao utenteDao;
    private final UtenteService utenteService;


    /*
    @Override
    public OrdineDto save(OrdineDto ordineDto, String encodedJwt) {
        try {
            // Decodifica del JWT base64 per ottenere il token JWT effettivo
            String jwt = new String(Base64.getDecoder().decode(encodedJwt));

            // Ottiene l'username (o email) dall'oggetto JWT usando il metodo TokenStore.getUser
            String username = TokenStore.getInstance().getUser(jwt);

            // Trova l'utente in base all'email
            Utente utente = utenteDao.findByCredenzialiEmail(username).orElse(null);

            // Se l'utente esiste, salva l'ordine
            if (utente != null) {
                // Mappa OrdineDto a Ordine
                Ordine ordine = modelMapper.map(ordineDto, Ordine.class);
                ordine.setUtente(utente); // Associa l'utente all'ordine

                // Salva l'ordine nel database
                ordine = ordineDao.save(ordine);

                // Mappa l'Ordine salvato di nuovo a OrdineDto e restituiscilo
                return modelMapper.map(ordine, OrdineDto.class);
            } else {
                // Se l'utente non esiste, restituisce null
                return null;
            }
        } catch (Exception e) {
            // Se si verifica un'eccezione, gestiscila o registrala se necessario
            e.printStackTrace(); // Puoi gestire le eccezioni in modo più appropriato a seconda del tuo caso d'uso
            return null;
        }
    }

     */

    @Override
    public void save(OrdineDto ordineDto) {
        Ordine o = modelMapper.map(ordineDto, Ordine.class);
        UtenteDto utenteDto = utenteService.getById(ordineDto.getIdutente());
        Utente utente =  modelMapper.map(utenteDto, Utente.class);
        o.setUtente(utente);
        ordineDao.save(o);
    }




    @Override
    public OrdineDto getById(Long id) {
        Ordine ordine = ordineDao.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Non esiste un articolo con id: [%s]", id)));
        return modelMapper.map(ordine, OrdineDto.class);
    }


    public Collection<OrdineDto> findOrderbyUser(Long id) {
        List<Ordine> ordini = ordineDao.findOrdiniByUtenteId(id);
        if (ordini.isEmpty()) {
            throw new EntityNotFoundException(String.format("Non ci sono ordini per l'utente con id: [%s]", id));
        }
        return ordini.stream()
                .map(ordine -> modelMapper.map(ordine, OrdineDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<OrdineDto> findAllOrders() {
        List<Ordine> ordini = ordineDao.findAll();
        return ordini.stream()
                .map(ordine -> modelMapper.map(ordine, OrdineDto.class))
                .collect(Collectors.toList());
    }
/*
    public Collection<OrdineDto> findOrderByUserAndDate(Long userId, String dateFilter) {
        List<Ordine> ordini = ordineDao.findOrdiniByUtenteId(userId);
        if (ordini.isEmpty()) {
            throw new EntityNotFoundException(String.format("Non ci sono ordini per l'utente con id: [%s]", userId));
        }

        // Filtra gli ordini in base alla stringa dateFilter
        List<Ordine> filteredOrders = ordini.stream()
                .filter(ordine -> ordine.getDataAcquisto().toString().contains(dateFilter))
                .collect(Collectors.toList());

        if (filteredOrders.isEmpty()) {
            throw new EntityNotFoundException(String.format("Nessun ordine trovato per l'utente con id [%s] e data contenente [%s]", userId, dateFilter));
        }

        return filteredOrders.stream()
                .map(ordine -> modelMapper.map(ordine, OrdineDto.class))
                .collect(Collectors.toList());
    }

 */

    @Override
    public Collection<OrdineProdottoDto> getDettagliOrdineByIdOrdine(Long id) {
        List<OrdineProdotto> ordineProdotto = ordineProdottoDao.findDettagliOrdineByOrdineId(id);

        // Converti gli oggetti OrdineProdotto in OrdineProdottoDto
        Collection<OrdineProdottoDto> ordineProdottoDtos = ordineProdotto.stream()
                .map(ordine -> modelMapper.map(ordine, OrdineProdottoDto.class))
                .collect(Collectors.toList());


        return ordineProdottoDtos;
    }
}
