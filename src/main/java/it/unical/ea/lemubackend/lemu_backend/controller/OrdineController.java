package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.data.service.OrdineService;
import it.unical.ea.lemubackend.lemu_backend.dto.OrdineDto;
import it.unical.ea.lemubackend.lemu_backend.dto.OrdineProdottoDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/ordinecontroller-api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class OrdineController {

    private final OrdineService ordineService;


    @PostMapping("/add")
    public void add(@RequestBody OrdineDto ordineDto) {
        ordineService.save(ordineDto);
    }



    @GetMapping("/all/{id}")
    public ResponseEntity<Collection<OrdineDto>> findbyUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ordineService.findOrderbyUser(id));
    }

    @GetMapping("/ordine/{orderId}")
    public ResponseEntity<OrdineDto> getById(@PathVariable("orderId") Long id) {
        OrdineDto ordine = ordineService.getById(id);
        return (ordine != null) ? ResponseEntity.ok(ordine) : ResponseEntity.notFound().build();    }


    @GetMapping("/getAll")
    public ResponseEntity<Collection<OrdineDto>> getAllOrders(){
        return ResponseEntity.ok(ordineService.findAllOrders());
    }

    @GetMapping("/getDettagliOrdini{idOrder}")
    public ResponseEntity<Collection<OrdineProdottoDto>> getDettagliOrdineByIdOrder(@PathVariable ("idOrder")Long id){
        return ResponseEntity.ok(ordineService.getDettagliOrdineByIdOrdine(id));
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
                //vanno gestiti gli errore
            }
        } catch (Exception e) {
            return null;

        }

        return null;
    }




    /*
    @GetMapping("/ordini/{idUser}/filter")
    public ResponseEntity<Collection<OrdineDto>> getOrdiniByUserAndDate(
            @PathVariable("idUser") Long idUser,
            @RequestParam("dateFilter") String dateFilter) {
        Collection<OrdineDto> ordini = ordineService.findOrderByUserAndDate(idUser, dateFilter);
        return ResponseEntity.ok(ordini);
    }

     */


}