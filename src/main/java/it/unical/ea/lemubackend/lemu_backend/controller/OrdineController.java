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
    public ResponseEntity<ApiResponse<OrdineDto>> add(@RequestBody OrdineDto ordine, @RequestParam String jwt) {
        if (ordineService.save(ordine, jwt)) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Prodotto aggiunto con successo", ordine));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Prodotto non aggiunto", null));
        }
    }

    @GetMapping("/ordini")
    public ResponseEntity<ApiResponse<Collection<OrdineDto>>> findbyUser(String jwt) {
        try {
            Collection<OrdineDto> ordini = ordineService.findOrderbyUser(jwt);
            if (ordini != null && !ordini.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "ordini presi con successo", ordini));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Nessun ordine trovato", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore durante il recupero degli ordini", null));
        }
    }

    @GetMapping("/ordine/{orderId}")
    public ResponseEntity<ApiResponse<OrdineDto>> getById(@PathVariable("orderId") Long id) {
        OrdineDto ordine = ordineService.getById(id);
        if (ordine != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Ordine trovato con successo", ordine));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Ordine non trovato", null));
        }
    }





}