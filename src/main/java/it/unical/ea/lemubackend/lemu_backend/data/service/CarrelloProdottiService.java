package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.dto.CarrelloProdottiDto;

import java.util.List;

public interface CarrelloProdottiService {
    CarrelloProdottiDto createCarrelloProdotti(CarrelloProdottiDto carrelloProdottiDto);
    CarrelloProdottiDto updateCarrelloProdotti(Long id, CarrelloProdottiDto carrelloProdottiDto);
    void deleteCarrelloProdotti(Long id);
    CarrelloProdottiDto getCarrelloProdottiById(Long id);
    List<CarrelloProdottiDto> getAllCarrelloProdotti();
}

