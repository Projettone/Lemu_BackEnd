package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.CarrelloDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Carrello;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.data.service.CarrelloService;
import it.unical.ea.lemubackend.lemu_backend.dto.CarrelloDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarrelloServiceImpl implements CarrelloService {

    @Autowired
    private CarrelloDao carrelloDao;

    @Override
    public CarrelloDto createCarrello(CarrelloDto carrelloDto) {
        Carrello carrello = new Carrello();
        //carrello.setUtente(new Utente(carrelloDto.getUtenteId()));
        carrello = carrelloDao.save(carrello);
        return convertToDto(carrello);
    }

    @Override
    public CarrelloDto updateCarrello(Long id, CarrelloDto carrelloDto) {
        Carrello carrello = carrelloDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrello not found"));
        //carrello.setUtente(new Utente(carrelloDto.getUtenteId()));
        carrello = carrelloDao.save(carrello);
        return convertToDto(carrello);
    }

    @Override
    public void deleteCarrello(Long id) {
        carrelloDao.deleteById(id);
    }

    @Override
    public CarrelloDto getCarrelloById(Long id) {
        Carrello carrello = carrelloDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrello not found"));
        return convertToDto(carrello);
    }

    @Override
    public List<CarrelloDto> getAllCarrelli() {
        List<Carrello> carrelloList = carrelloDao.findAll();
        return carrelloList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CarrelloDto convertToDto(Carrello carrello) {
        CarrelloDto carrelloDto = new CarrelloDto();
        carrelloDto.setId(carrello.getId());
        carrelloDto.setUtenteId(carrello.getUtente().getId());
        // carrelloDto.setProdotti(carrello.getCarrelloProdotti().stream()...
        return carrelloDto;
    }
}
