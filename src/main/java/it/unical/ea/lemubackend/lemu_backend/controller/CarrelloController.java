package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.data.service.CarrelloService;
import it.unical.ea.lemubackend.lemu_backend.dto.CarrelloDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrello-api")
public class CarrelloController {

    @Autowired
    private CarrelloService carrelloService;

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

    @GetMapping("/get/{id}")
    public ResponseEntity<CarrelloDto> getCarrelloById(@PathVariable Long id) {
        CarrelloDto carrelloDto = carrelloService.getCarrelloById(id);
        return ResponseEntity.ok(carrelloDto);
    }

    /*
    //Non mi serve
    @GetMapping("/all")
    public ResponseEntity<List<CarrelloDto>> getAllCarrelli() {
        List<CarrelloDto> carrelloList = carrelloService.getAllCarrelli();
        return ResponseEntity.ok(carrelloList);
    }
     */
}

