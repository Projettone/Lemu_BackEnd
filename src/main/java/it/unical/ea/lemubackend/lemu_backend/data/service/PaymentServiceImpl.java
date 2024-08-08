package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService{

    private final UtenteDao utenteDao;
    private final TokenStore tokenStore;

    @Autowired
    public PaymentServiceImpl(UtenteDao utenteDao, TokenStore tokenStore) {
        this.utenteDao = utenteDao;
        this.tokenStore = tokenStore;
    }

    @Override
    public ResponseEntity<?> processPayment(String token, Double amount) {
        try {
            Optional<Utente> utenteOptional = tokenStore.getUser(token);
            if (utenteOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
            }
            Utente u = utenteOptional.get();
            if (u.getSaldo() >= amount) {
                utenteDao.updateBalanceByEmail(u.getCredenziali().getEmail(), -amount);
                return ResponseEntity.ok("Pagamento effettuato con successo");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo insufficiente.");
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
