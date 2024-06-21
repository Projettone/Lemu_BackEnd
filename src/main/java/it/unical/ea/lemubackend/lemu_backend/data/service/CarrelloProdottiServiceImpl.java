package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.CarrelloProdottiDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Carrello;
import it.unical.ea.lemubackend.lemu_backend.data.entities.CarrelloProdotti;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Prodotto;
import it.unical.ea.lemubackend.lemu_backend.data.service.CarrelloProdottiService;
import it.unical.ea.lemubackend.lemu_backend.dto.CarrelloProdottiDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarrelloProdottiServiceImpl implements CarrelloProdottiService {

    @Autowired
    private CarrelloProdottiDao carrelloProdottiDao;

    @Override
    public CarrelloProdottiDto createCarrelloProdotti(CarrelloProdottiDto carrelloProdottiDto) {
        CarrelloProdotti carrelloProdotti = new CarrelloProdotti();
        carrelloProdotti.setQuantita(carrelloProdottiDto.getQuantita());
        //carrelloProdotti.setCarrello(new Carrello(carrelloProdottiDto.getCarrelloId()));
        //carrelloProdotti.setProdotto(new Prodotto(carrelloProdottiDto.getProdottoId()));
        carrelloProdotti = carrelloProdottiDao.save(carrelloProdotti);
        return convertToDto(carrelloProdotti);
    }

    @Override
    public CarrelloProdottiDto updateCarrelloProdotti(Long id, CarrelloProdottiDto carrelloProdottiDto) {
        CarrelloProdotti carrelloProdotti = carrelloProdottiDao.findById(id)
                .orElseThrow(() -> new RuntimeException("CarrelloProdotti not found"));
        carrelloProdotti.setQuantita(carrelloProdottiDto.getQuantita());
        //carrelloProdotti.setCarrello(new Carrello(carrelloProdottiDto.getCarrelloId()));
        //carrelloProdotti.setProdotto(new Prodotto(carrelloProdottiDto.getProdottoId()));
        carrelloProdotti = carrelloProdottiDao.save(carrelloProdotti);
        return convertToDto(carrelloProdotti);
    }

    @Override
    public void deleteCarrelloProdotti(Long id) {
        carrelloProdottiDao.deleteById(id);
    }

    @Override
    public CarrelloProdottiDto getCarrelloProdottiById(Long id) {
        CarrelloProdotti carrelloProdotti = carrelloProdottiDao.findById(id)
                .orElseThrow(() -> new RuntimeException("CarrelloProdotti not found"));
        return convertToDto(carrelloProdotti);
    }

    @Override
    public List<CarrelloProdottiDto> getAllCarrelloProdotti() {
        List<CarrelloProdotti> carrelloProdottiList = carrelloProdottiDao.findAll();
        return carrelloProdottiList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CarrelloProdottiDto convertToDto(CarrelloProdotti carrelloProdotti) {
        CarrelloProdottiDto carrelloProdottiDto = new CarrelloProdottiDto();
        carrelloProdottiDto.setId(carrelloProdotti.getId());
        carrelloProdottiDto.setCarrelloId(carrelloProdotti.getCarrello().getId());
        carrelloProdottiDto.setProdottoId(carrelloProdotti.getProdotto().getId());
        carrelloProdottiDto.setQuantita(carrelloProdotti.getQuantita());
        return carrelloProdottiDto;
    }
}


