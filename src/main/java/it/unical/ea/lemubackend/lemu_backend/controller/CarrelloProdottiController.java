package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.data.service.CarrelloProdottiService;
import it.unical.ea.lemubackend.lemu_backend.dto.CarrelloProdottiDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("carrelloprodotti-api")
public class CarrelloProdottiController {

    @Autowired
    private CarrelloProdottiService carrelloProdottiService;

    @PostMapping("/add")
    public ResponseEntity<CarrelloProdottiDto> createCarrelloProdotti(@RequestBody CarrelloProdottiDto carrelloProdottiDto) {
        CarrelloProdottiDto createdCarrelloProdotti = carrelloProdottiService.createCarrelloProdotti(carrelloProdottiDto);
        return ResponseEntity.ok(createdCarrelloProdotti);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CarrelloProdottiDto> updateCarrelloProdotti(@PathVariable Long id, @RequestBody CarrelloProdottiDto carrelloProdottiDto) {
        CarrelloProdottiDto updatedCarrelloProdotti = carrelloProdottiService.updateCarrelloProdotti(id, carrelloProdottiDto);
        return ResponseEntity.ok(updatedCarrelloProdotti);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCarrelloProdotti(@PathVariable Long id) {
        carrelloProdottiService.deleteCarrelloProdotti(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CarrelloProdottiDto> getCarrelloProdottiById(@PathVariable Long id) {
        CarrelloProdottiDto carrelloProdottiDto = carrelloProdottiService.getCarrelloProdottiById(id);
        return ResponseEntity.ok(carrelloProdottiDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarrelloProdottiDto>> getAllCarrelloProdotti() {
        List<CarrelloProdottiDto> carrelloProdottiList = carrelloProdottiService.getAllCarrelloProdotti();
        return ResponseEntity.ok(carrelloProdottiList);
    }
}

