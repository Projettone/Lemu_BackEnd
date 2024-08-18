package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.data.service.ProdottoService;
import it.unical.ea.lemubackend.lemu_backend.dto.ProdottoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.List;

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
    // @PreAuthorize("hasRole('ROLE_ADMIN')") //va aggiunto solo ai metodi che devino essere richiamati da admin
    public void add(@RequestBody ProdottoDto prodotto) {
        prodottoService.save(prodotto);
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<ProdottoDto>> findAll(@RequestParam int start, @RequestParam int end) {
        return ResponseEntity.ok(prodottoService.findAll(start, end));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ProdottoDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(prodottoService.getById(id));
    }


    @GetMapping("/search")
    public ResponseEntity<List<ProdottoDto>> searchProdotti(@RequestParam String keyword) {
        return ResponseEntity.ok(prodottoService.searchProdotti(keyword));
    }


    //prendere tutti i prodotti di una determinata categoria

    @GetMapping("/get/by-category/{categoria}")
    public ResponseEntity<List<ProdottoDto>> getProdutCategory(@PathVariable("categoria") String categoria){
        return ResponseEntity.ok(prodottoService.getProdottiByCategoria(categoria));
    }




}
