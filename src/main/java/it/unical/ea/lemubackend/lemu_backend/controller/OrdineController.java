package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.data.service.OrdineService;
import it.unical.ea.lemubackend.lemu_backend.dto.OrdineDto;
import it.unical.ea.lemubackend.lemu_backend.dto.OrdineProdottoDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ordinecontroller-api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class OrdineController {

    private final OrdineService ordineService;


    @PostMapping("/add")
    public ResponseEntity<Long> add(@RequestBody OrdineDto ordineDto) {
        try {
            Long ordineId = ordineService.save(ordineDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ordineId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/update/{ordineId}")
    public ResponseEntity<String> updateOrdineProdotti(
            @PathVariable Long ordineId,
            @RequestBody List<OrdineProdottoDto> ordineProdottiDto) {
        try {
            ordineService.updateOrdineProdotti(ordineId, ordineProdottiDto);
            return ResponseEntity.ok("Ordine aggiornato con successo");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore nel server");
        }
    }


    @GetMapping("/all/{id}")
    public ResponseEntity<Collection<OrdineDto>> findbyUser(@PathVariable("id") Long id) {
        try {
            Collection<OrdineDto> ordini = ordineService.findOrderbyUser(id);
            return ResponseEntity.ok(ordini);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }


    @GetMapping("/ordine/{orderId}")
    public ResponseEntity<OrdineDto> getById(@PathVariable("orderId") Long id) {
        try {
            OrdineDto ordine = ordineService.getById(id);
            return (ordine != null) ? ResponseEntity.ok(ordine) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<Collection<OrdineDto>> getAllOrders() {
        try {
            Collection<OrdineDto> ordini = ordineService.findAllOrders();
            return ResponseEntity.ok(ordini);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }


    @GetMapping("/getDettagliOrdini{idOrder}")
    public ResponseEntity<Collection<OrdineProdottoDto>> getDettagliOrdineByIdOrder(@PathVariable("idOrder") Long id) {
        try {
            Collection<OrdineProdottoDto> dettagli = ordineService.getDettagliOrdineByIdOrdine(id);
            return ResponseEntity.ok(dettagli);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }



    @GetMapping("/getOrdiniByidUtente{idUser}")
    public ResponseEntity<Collection<OrdineDto>> getOrdinibyidUtente(HttpServletRequest request, @PathVariable ("idUser")Long id){
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = TokenStore.getInstance().getToken(request);
                Optional<Utente> optional = TokenStore.getInstance().getUser(token);
                if(optional.isPresent()){
                    return ResponseEntity.ok(ordineService.findOrderbyUser(optional.get().getId()));
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
        return null;
    }

}