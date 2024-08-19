package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.data.service.CarrelloProdottiService;
import it.unical.ea.lemubackend.lemu_backend.data.service.CarrelloService;
import it.unical.ea.lemubackend.lemu_backend.dto.CarrelloDto;
import it.unical.ea.lemubackend.lemu_backend.dto.CarrelloProdottiDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrello-api")
public class CarrelloController {

    @Autowired
    private CarrelloService carrelloService;

    @Autowired
    private CarrelloProdottiService carrelloProdottiService;

    // Carrello

    @PostMapping("/add")
    public ResponseEntity<CarrelloDto> createCarrello(@RequestBody CarrelloDto carrelloDto) {
        CarrelloDto createdCarrello = carrelloService.createCarrello(carrelloDto);
        return ResponseEntity.ok(createdCarrello);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CarrelloDto> updateCarrello(@PathVariable Long id, @RequestBody CarrelloDto carrelloDto) {
        CarrelloDto updatedCarrello = carrelloService.updateCarrello(id, carrelloDto);
        return ResponseEntity.ok(updatedCarrello);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCarrello(@PathVariable Long id) {
        carrelloService.deleteCarrello(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getByUtente/{utenteId}")
    public ResponseEntity<CarrelloDto> getCarrelloByUtenteId(@PathVariable Long utenteId) {
        CarrelloDto carrelloDto = carrelloService.getCarrelloByUtenteId(utenteId);
        return ResponseEntity.ok(carrelloDto);
    }

    // CarrelloProdotti

    @PostMapping("/prodotti/add")
    public ResponseEntity<CarrelloProdottiDto> createCarrelloProdotti(@RequestBody CarrelloProdottiDto carrelloProdottiDto) {
        CarrelloProdottiDto createdCarrelloProdotti = carrelloProdottiService.createCarrelloProdotti(carrelloProdottiDto);
        return ResponseEntity.ok(createdCarrelloProdotti);
    }

    @PutMapping("/prodotti/update/{id}")
    public ResponseEntity<CarrelloProdottiDto> updateCarrelloProdotti(@PathVariable Long id, @RequestBody CarrelloProdottiDto carrelloProdottiDto) {
        CarrelloProdottiDto updatedCarrelloProdotti = carrelloProdottiService.updateCarrelloProdotti(id, carrelloProdottiDto);
        return ResponseEntity.ok(updatedCarrelloProdotti);
    }

    @DeleteMapping("/prodotti/delete/{id}")
    public ResponseEntity<Void> deleteCarrelloProdotti(@PathVariable Long id) {
        carrelloProdottiService.deleteCarrelloProdotti(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/prodotti/get/{id}")
    public ResponseEntity<CarrelloProdottiDto> getCarrelloProdottiById(@PathVariable Long id) {
        CarrelloProdottiDto carrelloProdottiDto = carrelloProdottiService.getCarrelloProdottiById(id);
        return ResponseEntity.ok(carrelloProdottiDto);
    }

    @GetMapping("/prodotti/get/{carrelloid}/all")
    public ResponseEntity<List<CarrelloProdottiDto>> getAllCarrelloProdottiByCarrelloId(@PathVariable Long carrelloid) {
        List<CarrelloProdottiDto> carrelloProdottiList = carrelloProdottiService.getAllCarrelloProdottiByCarrelloId(carrelloid);
        return ResponseEntity.ok(carrelloProdottiList);
    }
}
