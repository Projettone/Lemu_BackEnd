package it.unical.ea.lemubackend.lemu_backend.controller;

import com.nimbusds.jose.JOSEException;
import it.unical.ea.lemubackend.lemu_backend.config.ApiResponse;
import it.unical.ea.lemubackend.lemu_backend.data.service.ProdottoService;
import it.unical.ea.lemubackend.lemu_backend.dto.ProdottoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prodottocontroller-api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdottoController {

    private final ProdottoService prodottoService;


/*
    @PostMapping("/add")
    public ResponseEntity<ProdottoDto> add(@RequestBody @Valid ProdottoDto prodotto, @RequestParam String jwt) {
        return ResponseEntity.ok(prodottoService.save(prodotto, jwt));

    }

 */

    @PostMapping("/add")
    public void add(@RequestBody ProdottoDto prodotto) {
        prodottoService.save(prodotto);
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<ProdottoDto>> findAll() {
        return ResponseEntity.ok(prodottoService.findAll());
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ProdottoDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(prodottoService.getById(id));
    }




}
