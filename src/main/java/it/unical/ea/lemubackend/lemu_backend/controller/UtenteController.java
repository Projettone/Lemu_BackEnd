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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
            if (response.getStatusCode().isSameCodeAs(HttpStatus.FORBIDDEN)){
                return new ApiResponse<>(false, HttpStatus.FORBIDDEN.toString(), "Banned user account");
            }
            String token = TokenStore.getInstance().extractToken(response);
            return new ApiResponse<>(true, response.getStatusCode().toString(), token);
        } catch (Exception e) {
            return new ApiResponse<>(false, HttpStatus.BAD_REQUEST.toString(), "Error: " + e.getMessage());
        }
    }



    @PostMapping(path = "/authenticate")
    public ApiResponse<String> authenticate(@RequestParam("email") String email, @RequestParam("password") String password) throws JOSEException {
        if (utenteService.checkBan(email)) {
            return new ApiResponse<>(false, HttpStatus.FORBIDDEN.toString(), "Banned user account");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String token = TokenStore.getInstance().createToken(Map.of("email", email));
        return new ApiResponse<>(true, HttpStatus.OK.toString(), token);
    }


    @PostMapping("/register")
    public ApiResponse<String> registerUser(@RequestBody UtenteRegistrazioneDto utenteRegistrazioneDto) {
        try {
            if(!utenteService.checkBan(utenteRegistrazioneDto.getCredenzialiEmail())){
                ResponseEntity<?> response = utenteService.registerUser(utenteRegistrazioneDto);
                String token = TokenStore.getInstance().extractToken(response);
                return new ApiResponse<>(true, response.getStatusCode().toString(), token);
            }else{
                return new ApiResponse<>(false, HttpStatus.FORBIDDEN.toString(), "Banned user account");
            }
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
                utenteService.updateShippingAddress(token, address);
                return new ApiResponse<>(true, HttpStatus.OK.toString(), "Email updated successfully");
            } else {
                return new ApiResponse<>(false, HttpStatus.UNAUTHORIZED.toString(), "Unauthorized");
            }
        } catch (Exception e) {
            return new ApiResponse<>(false, HttpStatus.BAD_REQUEST.toString(), "Error: " + e.getMessage());
        }
    }


    @PostMapping("/ban-user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> banUser(@RequestParam("email") String email) {
        try {
            boolean result = utenteService.banUser(email);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/unban-user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> unbanUser(@RequestParam("email") String email) {
        try {
            boolean result = utenteService.unbanUser(email);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/make-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> makeAdmin(@RequestParam("email") String email) {
        try {
            boolean result = utenteService.makeAdmin(email);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/revoke-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> revokeAdmin(@RequestParam("email") String email) {
        try {
            boolean result = utenteService.revokeAdmin(email);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<UtenteDto>> searchUsers(@RequestParam("keyword") String keyword) {
        try {
            List<UtenteDto> user = utenteService.searchUsers(keyword);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
