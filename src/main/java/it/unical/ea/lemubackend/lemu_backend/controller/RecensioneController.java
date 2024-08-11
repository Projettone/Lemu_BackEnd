package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.data.service.RecensioneService;
import it.unical.ea.lemubackend.lemu_backend.dto.RecensioneDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path="/recensione-api")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RecensioneController
{

    private final RecensioneService recensioneService;

    @GetMapping("/getByIdProdotto/{id}")
    public ResponseEntity<List<RecensioneDto>> getRecensioniByProdottoId(@PathVariable("id") Long prodottoId) {
        try {
            List<RecensioneDto> recensioni = recensioneService.findAllByIdProdotto(prodottoId);
            return new ResponseEntity<>(recensioni, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }}
