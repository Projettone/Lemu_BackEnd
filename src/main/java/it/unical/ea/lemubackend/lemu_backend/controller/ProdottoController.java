package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.data.service.ProdottoService;
import it.unical.ea.lemubackend.lemu_backend.dto.ProdottoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prodottocontroller-api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdottoController {

    private final ProdottoService prodottoService;




    @PostMapping("/add")
    public ResponseEntity<Void> add(@RequestBody ProdottoDto prodotto) {
        try {
            prodottoService.save(prodotto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<ProdottoDto>> findAll(@RequestParam int start, @RequestParam int end) {
        try {
            Collection<ProdottoDto> prodotti = prodottoService.findAll(start, end);
            return ResponseEntity.ok(prodotti);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }



    @GetMapping("/get/{id}")
    public ResponseEntity<ProdottoDto> getById(@PathVariable("id") Long id) {
        try {
            ProdottoDto prodotto = prodottoService.getById(id);
            return (prodotto != null) ? ResponseEntity.ok(prodotto) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/get-by-userId{id}")
    public ResponseEntity<List<ProdottoDto>> getByUserId(@PathVariable("id") Long id) {
        try {
            List<ProdottoDto> prodotti = prodottoService.getByUserId(id);
            return ResponseEntity.ok(prodotti);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }



    @GetMapping("/search")
    public ResponseEntity<List<ProdottoDto>> searchProdotti(@RequestParam String keyword) {
        try {
            List<ProdottoDto> prodotti = prodottoService.searchProdotti(keyword);
            return ResponseEntity.ok(prodotti);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }



    @GetMapping("/get/by-category/{categoria}")
    public ResponseEntity<List<ProdottoDto>> getProdutCategory(@PathVariable("categoria") String categoria) {
        try {
            List<ProdottoDto> prodotti = prodottoService.getProdottiByCategoria(categoria);
            return ResponseEntity.ok(prodotti);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        try {
            prodottoService.deleteById(id);
            return ResponseEntity.noContent().build(); // Restituisce un 204 No Content
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





}
