package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.CarrelloDao;
import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Carrello;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.CarrelloDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarrelloServiceImpl implements CarrelloService {

    @Autowired
    private CarrelloDao carrelloDao;

    private final ModelMapper modelmapper;

    public CarrelloServiceImpl(ModelMapper modelmapper) {
        this.modelmapper = modelmapper;
    }

    @Override
    public CarrelloDto createCarrello(CarrelloDto carrelloDto) {
        Carrello carrello = modelmapper.map(carrelloDto,Carrello.class);
        carrello = carrelloDao.save(carrello);
        return convertToDto(carrello);
    }

    @Override
    public CarrelloDto updateCarrello(Long id, CarrelloDto carrelloDto) {
        // Trova il carrello per ID, altrimenti restituisci null o esegui un'azione appropriata
        Carrello carrello = carrelloDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrello not found")); // Commento: qui puoi aggiungere la tua logica di gestione degli errori

        // Salva le modifiche nel database
        carrello = carrelloDao.save(carrello);

        // Converte l'entità Carrello in CarrelloDto
        return convertToDto(carrello);
    }


    @Override
    public void deleteCarrello(Long id) {
        if (carrelloDao.existsById(id)) {
            carrelloDao.deleteById(id);
        }
    }

    @Override
    public CarrelloDto getCarrelloByUtenteId(Long utenteId) {
        Carrello carrello = carrelloDao.findByUtenteId(utenteId)
                .orElseThrow(() -> new RuntimeException("Carrello not found for user id: " + utenteId));
        return convertToDto(carrello);
    }


    private CarrelloDto convertToDto(Carrello carrello) {
        CarrelloDto carrelloDto = new CarrelloDto();
        carrelloDto.setId(carrello.getId());
        carrelloDto.setUtenteId(carrello.getUtente().getId());
        // Converti altri campi se necessario
        return carrelloDto;
    }

}