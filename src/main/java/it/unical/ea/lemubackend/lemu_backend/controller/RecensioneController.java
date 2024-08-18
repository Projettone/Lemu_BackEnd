package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.data.service.RecensioneService;
import it.unical.ea.lemubackend.lemu_backend.dto.RecensioneDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping(path="/recensione-api")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RecensioneController
{

    private final RecensioneService recensioneService;


    @PostMapping("/add")
    public ResponseEntity<?> addRecensione(@RequestBody RecensioneDto recensioneDto) {
        return recensioneService.save(recensioneDto);
    }



    @GetMapping("/getByIdProdotto/{id}")
    public ResponseEntity<List<RecensioneDto>> getRecensioniByProdottoId(@PathVariable("id") Long prodottoId) {
        try {
            List<RecensioneDto> recensioni = recensioneService.findAllByIdProdotto(prodottoId);
            return new ResponseEntity<>(recensioni, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/get-reviews")
    public ResponseEntity<Page<RecensioneDto>> getRecensioniByUtente(HttpServletRequest request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = TokenStore.getInstance().getToken(request);
                Optional<Utente> u = TokenStore.getInstance().getUser(token);
                if (u.isPresent()){
                    Pageable pageable = PageRequest.of(page, size);
                    return recensioneService.findAllByUtente(u.get(), pageable);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(HttpServletRequest request, @PathVariable Long id) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = TokenStore.getInstance().getToken(request);
                boolean deleted = recensioneService.deleteReview(token, id);
                if (deleted) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
