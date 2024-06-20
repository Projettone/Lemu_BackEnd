package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.data.service.OrdineService;
import it.unical.ea.lemubackend.lemu_backend.dto.OrdineDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/ordini")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class OrdineController {

    private final OrdineService ordineService;


    @PostMapping("/ordine")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid OrdineDto ordine, @RequestParam String jwt) {
        return ResponseEntity.ok(ordineService.save(ordine, jwt));
    }

    @GetMapping("/ordine")
    public ResponseEntity<Collection<OrdineDto>> findbyUser(String jwt) {
        return ResponseEntity.ok(ordineService.findOrderbyUser(jwt));
    }

    @GetMapping("/ordine/{ordineId}")
    public ResponseEntity<OrdineDto> getById(@PathVariable("orderId") Long id) {
        OrdineDto ordine = ordineService.getById(id);
        return (ordine != null) ? ResponseEntity.ok(ordine) : ResponseEntity.notFound().build();
    }




}