package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(path="/payment-api")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/process-payment/{amount}")
    public ResponseEntity<?> processPayment(HttpServletRequest request, @PathVariable Double amount) {
        String token = TokenStore.getInstance().getToken(request);
        try {
            if (token != null && !"invalid".equals(token)){
                return paymentService.processPayment(token, amount);
            } else {
                return new ResponseEntity<>("Authorization header missing or invalid", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
