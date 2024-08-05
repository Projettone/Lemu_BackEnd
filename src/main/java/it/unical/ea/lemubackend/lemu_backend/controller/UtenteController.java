package it.unical.ea.lemubackend.lemu_backend.controller;

import com.nimbusds.jose.JOSEException;
import it.unical.ea.lemubackend.lemu_backend.config.ApiResponse;
import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Indirizzo;
import it.unical.ea.lemubackend.lemu_backend.data.service.UtenteService;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteDto;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteRegistrazioneDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping(path="/utente-api")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UtenteController {

    private final UtenteService utenteService;
    private final AuthenticationManager authenticationManager;


    @GetMapping("/google_login")
    public ApiResponse<String> googleAuthentication(@RequestParam("idToken") String idTokenString) {
        try {
            ResponseEntity<?> response = utenteService.googleAuthentication(idTokenString);
            String token = TokenStore.getInstance().extractToken(response);
            return new ApiResponse<>(true, response.getStatusCode().toString(), token);
        } catch (Exception e) {
            return new ApiResponse<>(false, HttpStatus.BAD_REQUEST.toString(), "Error: " + e.getMessage());
        }
    }



    @PostMapping(path = "/authenticate")
    public ApiResponse<String> authenticate(@RequestParam("email") String email, @RequestParam("password") String password) throws JOSEException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String token = TokenStore.getInstance().createToken(Map.of("email", email));
        return new ApiResponse<>(true, HttpStatus.OK.toString(), token);
    }


    @PostMapping("/register")
    public ApiResponse<String> registerUser(@RequestBody UtenteRegistrazioneDto utenteRegistrazioneDto) {
        try {
            ResponseEntity<?> response = utenteService.registerUser(utenteRegistrazioneDto);
            String token = TokenStore.getInstance().extractToken(response);
            return new ApiResponse<>(true, response.getStatusCode().toString(), token);
        } catch (Exception e) {
            return new ApiResponse<>(false, HttpStatus.BAD_REQUEST.toString(), "Error: " + e.getMessage());
        }
    }

    @GetMapping("/user")
    public ApiResponse<UtenteDto> getUserData(HttpServletRequest request) {
        UtenteDto user = null;
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = TokenStore.getInstance().getToken(request);
                user = utenteService.getUserByToken(token);
                return new ApiResponse<>(true, HttpStatus.OK.toString(), user);
            } else {
                return new ApiResponse<>(false, HttpStatus.UNAUTHORIZED.toString(), user);
            }
        } catch (Exception e) {
            return new ApiResponse<>(false, HttpStatus.BAD_REQUEST.toString(), user);
        }
    }



    @PostMapping("/update-password")
    public ApiResponse<String> updatePassword(@RequestParam("newPassword") String newPassword, HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = TokenStore.getInstance().getToken(request);
                utenteService.updatePassword(token, newPassword);
                return new ApiResponse<>(true, HttpStatus.OK.toString(), "Password updated successfully");
            } else {
                return new ApiResponse<>(false, HttpStatus.UNAUTHORIZED.toString(), "Unauthorized");
            }
        } catch (Exception e) {
            return new ApiResponse<>(false, HttpStatus.BAD_REQUEST.toString(), "Error: " + e.getMessage());
        }
    }

    @PostMapping("/update-shipping-address")
    public ApiResponse<String> updateShippingAddress(@RequestBody Indirizzo address, HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = TokenStore.getInstance().getToken(request);
                System.out.println("INDIRIZZOOO: "+address);
                utenteService.updateShippingAddress(token, address);
                return new ApiResponse<>(true, HttpStatus.OK.toString(), "Email updated successfully");
            } else {
                return new ApiResponse<>(false, HttpStatus.UNAUTHORIZED.toString(), "Unauthorized");
            }
        } catch (Exception e) {
            return new ApiResponse<>(false, HttpStatus.BAD_REQUEST.toString(), "Error: " + e.getMessage());
        }
    }





}
