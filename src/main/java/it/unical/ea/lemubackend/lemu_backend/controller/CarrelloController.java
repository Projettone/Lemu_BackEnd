package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.data.service.CarrelloService;
import it.unical.ea.lemubackend.lemu_backend.dto.CarrelloDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrello")
public class CarrelloController {

    @Autowired
    private CarrelloService carrelloService;

    @PostMapping
    public ResponseEntity<CarrelloDto> createCarrello(@RequestBody CarrelloDto carrelloDto) {
        CarrelloDto createdCarrello = carrelloService.createCarrello(carrelloDto);
        return ResponseEntity.ok(createdCarrello);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarrelloDto> updateCarrello(@PathVariable Long id, @RequestBody CarrelloDto carrelloDto) {
        CarrelloDto updatedCarrello = carrelloService.updateCarrello(id, carrelloDto);
        return ResponseEntity.ok(updatedCarrello);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrello(@PathVariable Long id) {
        carrelloService.deleteCarrello(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarrelloDto> getCarrelloById(@PathVariable Long id) {
        CarrelloDto carrelloDto = carrelloService.getCarrelloById(id);
        return ResponseEntity.ok(carrelloDto);
    }

    @GetMapping
    public ResponseEntity<List<CarrelloDto>> getAllCarrelli() {
        List<CarrelloDto> carrelloList = carrelloService.getAllCarrelli();
        return ResponseEntity.ok(carrelloList);
    }
}

