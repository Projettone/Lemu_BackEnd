package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.dto.CarrelloDto;

import java.util.List;

public interface CarrelloService {
    CarrelloDto createCarrello(CarrelloDto carrelloDto);
    CarrelloDto updateCarrello(Long id, CarrelloDto carrelloDto);
    void deleteCarrello(Long id);
    CarrelloDto getCarrelloById(Long id);
    List<CarrelloDto> getAllCarrelli();
}

