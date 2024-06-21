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
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdottoController {

    private final ProdottoService prodottoService;



    @PostMapping("/prodotto")
    public ResponseEntity<ApiResponse<ProdottoDto>> add(@RequestBody ProdottoDto prodotto, @RequestParam String jwt) {
        if (prodottoService.save(prodotto, jwt)) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Prodotto aggiunto con successo", prodotto));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Prodotto non aggiunto", null));
        }
    }


    @GetMapping("/prodotti")
    public ResponseEntity<ApiResponse<Collection<ProdottoDto>>> findAll() {
        try {
            Collection<ProdottoDto> prodotti = prodottoService.findAll();
            if (prodotti != null && !prodotti.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Prodotti presi con successo", prodotti));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Nessun prodotto trovato", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore durante il recupero dei prodotti", null));
        }
    }


    @GetMapping("/prodotto/{idItem}")
    public ResponseEntity<ProdottoDto> getById(@PathVariable("idItem") Long id) {
        return ResponseEntity.ok(prodottoService.getById(id));
    }




}
