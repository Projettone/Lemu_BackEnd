package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.config.ApiResponse;
import it.unical.ea.lemubackend.lemu_backend.data.service.OrdineService;
import it.unical.ea.lemubackend.lemu_backend.dto.OrdineDto;
import it.unical.ea.lemubackend.lemu_backend.dto.ProdottoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class OrdineController {

    private final OrdineService ordineService;


    @PostMapping("/ordine")
    public ResponseEntity<OrdineDto> add(@RequestBody OrdineDto ordine, @RequestParam String jwt) {
     return ResponseEntity.ok(ordineService.save(ordine, jwt));

    }

    @GetMapping("/ordini")
    public ResponseEntity<Collection<OrdineDto>> findbyUser(String jwt) {
        Collection<OrdineDto> ordineDtos = ordineService.findOrderbyUser(jwt);
        return (ordineDtos != null) ? ResponseEntity.ok(ordineDtos) : ResponseEntity.notFound().build();
    }

    @GetMapping("/ordine/{orderId}")
    public ResponseEntity<OrdineDto> getById(@PathVariable("orderId") Long id) {
        OrdineDto ordine = ordineService.getById(id);
        return (ordine != null) ? ResponseEntity.ok(ordine) : ResponseEntity.notFound().build();    }





}