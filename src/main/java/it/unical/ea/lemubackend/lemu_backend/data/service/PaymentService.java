package it.unical.ea.lemubackend.lemu_backend.data.service;

import org.springframework.http.ResponseEntity;

public interface PaymentService {

    ResponseEntity<?> processPayment(String token, Double amount);
}
