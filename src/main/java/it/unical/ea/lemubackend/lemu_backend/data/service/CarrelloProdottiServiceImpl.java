package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.CarrelloDao;
import it.unical.ea.lemubackend.lemu_backend.data.dao.CarrelloProdottiDao;
import it.unical.ea.lemubackend.lemu_backend.data.dao.ProdottoDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Carrello;
import it.unical.ea.lemubackend.lemu_backend.data.entities.CarrelloProdotti;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Prodotto;
import it.unical.ea.lemubackend.lemu_backend.dto.CarrelloProdottiDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarrelloProdottiServiceImpl implements CarrelloProdottiService {

    @Autowired
    private CarrelloProdottiDao carrelloProdottiDao;

    @Autowired
    private CarrelloDao carrelloDao;

    @Autowired
    private ProdottoDao prodottoDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CarrelloProdottiDto createCarrelloProdotti(CarrelloProdottiDto carrelloProdottiDto) {
        Carrello carrello = carrelloDao.findById(carrelloProdottiDto.getCarrelloId())
                .orElseThrow(() -> new IllegalArgumentException("Carrello non trovato con ID: " + carrelloProdottiDto.getCarrelloId()));
        Prodotto prodotto = prodottoDao.findById(carrelloProdottiDto.getProdottoId())
                .orElseThrow(() -> new IllegalArgumentException("Prodotto non trovato con ID: " + carrelloProdottiDto.getProdottoId()));

        CarrelloProdotti carrelloProdotti = modelMapper.map(carrelloProdottiDto, CarrelloProdotti.class);
        carrelloProdotti.setQuantita(carrelloProdottiDto.getQuantita());
        carrelloProdotti.setCarrello(carrello);
        carrelloProdotti.setProdotto(prodotto);

        carrelloProdotti = carrelloProdottiDao.save(carrelloProdotti);

        return convertToDto(carrelloProdotti);
    }


    @Override
    public CarrelloProdottiDto updateCarrelloProdotti(Long id, CarrelloProdottiDto carrelloProdottiDto) {
        CarrelloProdotti carrelloProdotti = carrelloProdottiDao.findById(id)
                .orElseThrow(() -> new RuntimeException("CarrelloProdotti not found with ID: " + id));

        carrelloProdotti.setQuantita(carrelloProdottiDto.getQuantita());

        if (carrelloProdottiDto.getCarrelloId() != null &&
                !carrelloProdottiDto.getCarrelloId().equals(carrelloProdotti.getCarrello().getId())) {
            Carrello carrello = carrelloDao.findById(carrelloProdottiDto.getCarrelloId())
                    .orElseThrow(() -> new RuntimeException("Carrello not found with ID: " + carrelloProdottiDto.getCarrelloId()));
            carrelloProdotti.setCarrello(carrello);
        }

        if (carrelloProdottiDto.getProdottoId() != null &&
                !carrelloProdottiDto.getProdottoId().equals(carrelloProdotti.getProdotto().getId())) {
            Prodotto prodotto = prodottoDao.findById(carrelloProdottiDto.getProdottoId())
                    .orElseThrow(() -> new RuntimeException("Prodotto not found with ID: " + carrelloProdottiDto.getProdottoId()));
            carrelloProdotti.setProdotto(prodotto);
        }

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
    public List<CarrelloProdottiDto> getAllCarrelloProdottiByCarrelloId(Long carrelloId) {
        List<CarrelloProdotti> carrelloProdottiList = carrelloProdottiDao.findAllByCarrello_Id(carrelloId);
        return carrelloProdottiList.stream()
                .map(carrelloProdotti -> modelMapper.map(carrelloProdotti, CarrelloProdottiDto.class))
                .toList();
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