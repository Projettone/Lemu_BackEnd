package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.data.service.ProdottoService;
import it.unical.ea.lemubackend.lemu_backend.dto.ProdottoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/prodotti")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdottoController {

    private final ProdottoService prodottoService;



    @PostMapping("/prodotti")
    public ResponseEntity<ProdottoDto> add(@RequestBody @Valid ProdottoDto prodotto, @RequestParam String jwt) {
        return ResponseEntity.ok(prodottoService.save(prodotto, jwt));
    }

    @GetMapping("/prodotti")
    public ResponseEntity<Collection<ProdottoDto>> findAll() {
        return ResponseEntity.ok(prodottoService.findAll());
    }

    @GetMapping("/prodotti/{idItem}")
    public ResponseEntity<ProdottoDto> getById(@PathVariable("idItem") Long id) {
        return ResponseEntity.ok(prodottoService.getById(id));
    }




}
