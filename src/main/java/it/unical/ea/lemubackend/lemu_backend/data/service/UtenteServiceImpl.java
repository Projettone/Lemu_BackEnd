package it.unical.ea.lemubackend.lemu_backend.data.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.nimbusds.jose.JOSEException;
import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.dao.CouponDao;
import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Coupon;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Credenziali;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Indirizzo;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteDto;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteRegistrazioneDto;
import jakarta.persistence.EntityNotFoundException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;


@Service
public class UtenteServiceImpl implements UtenteService, UserDetailsService {
    private final UtenteDao utenteDao;
    private final ModelMapper modelMapper;
    private final TokenStore tokenStore;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Autowired
    public UtenteServiceImpl(UtenteDao utenteDao, ModelMapper modelMapper, TokenStore tokenStore, PasswordEncoder passwordEncoder) {
        this.utenteDao = utenteDao;
        this.modelMapper = modelMapper;
        this.tokenStore = tokenStore;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(Utente utente) { utenteDao.save(utente); }

    @Override
    public ResponseEntity<?> registerUser(UtenteRegistrazioneDto utenteRegistrazioneDto) throws JOSEException {
        if(utenteDao.findByCredenzialiEmail(utenteRegistrazioneDto.getCredenzialiEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }

        Utente utente = new Utente();
        utente.setNome(utenteRegistrazioneDto.getNome());
        utente.setCognome(utenteRegistrazioneDto.getCognome());
        Credenziali c = new Credenziali(utenteRegistrazioneDto.getCredenzialiEmail(), passwordEncoder.encode(utenteRegistrazioneDto.getCredenzialiPassword()));
        utente.setCredenziali(c);
        utente.setIsAdmin(false);
        utente.setSaldo(0);
        utente.setBannato(false);

        Path path = Paths.get("src/main/resources/static/placeholder.png");
        String img;
        try{
            byte[] b = Files.readAllBytes(path);
            img = Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        utente.setImmagineProfilo("data:image/png;base64,"+img);

        utenteDao.save(utente);

        String token = tokenStore.createToken(Map.of("email", c.getEmail()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return ResponseEntity.ok().headers(headers).build();
    }

    @Override
    public UtenteDto getById(Long id) {
        Utente utente = utenteDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Non esiste un utente con id: [%s]", id)));
        return modelMapper.map(utente, UtenteDto.class);
    }

    @Override
    public UtenteDto getByCEmail(String email) {
        Utente utente = utenteDao.findByCredenzialiEmail(email).orElseThrow(() -> new EntityNotFoundException(
                        String.format("La seguente email non è presente: [%s]", email)));
        return modelMapper.map(utente, UtenteDto.class);
    }


    @Override
    public ResponseEntity<?> googleAuthentication(String idToken) throws JOSEException, GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken token = verifier.verify(idToken);
        if (idToken != null) {
            GoogleIdToken.Payload payload = token.getPayload();

            String userId = payload.getSubject();
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            Optional<Utente> existingUser = utenteDao.findByCredenzialiEmail(email);
            if (existingUser.isPresent() && existingUser.get().getBannato()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            if (existingUser.isEmpty()) {
                Utente utente = new Utente();
                utente.setNome(givenName);
                utente.setCognome(familyName);
                Credenziali c = new Credenziali(email, "");
                utente.setCredenziali(c);
                utente.setIsAdmin(false);
                utente.setImmagineProfilo(pictureUrl);
                utente.setBannato(false);

                utenteDao.save(utente);
            }

            String jwtToken = tokenStore.createToken(Map.of("email", email));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + jwtToken);
            return ResponseEntity.ok().headers(headers).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed authentication");
    }

    public ResponseEntity<?> facebookAuthentication(String accessToken) throws JOSEException {
        String url = "https://graph.facebook.com/me?fields=id,email,first_name,last_name,picture&access_token=" + accessToken;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            JSONObject userData = new JSONObject(response.getBody());

            String email = userData.optString("email");
            String firstName = userData.optString("first_name");
            String lastName = userData.optString("last_name");
            String pictureUrl = userData.optJSONObject("picture").optJSONObject("data").optString("url");

            Optional<Utente> existingUser = utenteDao.findByCredenzialiEmail(email);
            if (existingUser.isPresent() && existingUser.get().getBannato()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            if (existingUser.isEmpty()) {
                Utente utente = new Utente();
                utente.setNome(firstName);
                utente.setCognome(lastName);
                Credenziali credenziali = new Credenziali(email, "");
                utente.setCredenziali(credenziali);
                utente.setIsAdmin(false);
                utente.setImmagineProfilo(pictureUrl);
                utente.setBannato(false);

                utenteDao.save(utente);
            }

            String jwtToken = tokenStore.createToken(Map.of("email", email));
            HttpHeaders jwtHeaders = new HttpHeaders();
            jwtHeaders.add("Authorization", "Bearer " + jwtToken);

            return ResponseEntity.ok().headers(jwtHeaders).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed authentication");
    }



    @Override
    public UtenteDto getUserByToken(String token) throws ParseException, JOSEException {
        Optional<Utente> utente = tokenStore.getUser(token);
        if (utente.isPresent()) {
            return modelMapper.map(utente.get(), UtenteDto.class);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void updatePassword(String token, String newPassword) throws ParseException, JOSEException {
        Optional<Utente> utenteOptional = tokenStore.getUser(token);
        if (utenteOptional.isPresent()) {
            Utente utente = utenteOptional.get();
            utente.getCredenziali().setPassword(passwordEncoder.encode(newPassword));
            utenteDao.save(utente);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }


    @Override
    public void updateShippingAddress(String token, Indirizzo address) throws ParseException, JOSEException {
        Optional<Utente> utenteOptional = tokenStore.getUser(token);
        if (utenteOptional.isPresent()) {
            Utente utente = utenteOptional.get();
            utente.setIndirizzo(address);
            System.out.println("ADDRESS: "+address);
            utenteDao.save(utente);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    @Override
    public Boolean banUser(String email) {
        Optional<Utente> utenteOptional = utenteDao.findByCredenzialiEmail(email);
        if (utenteOptional.isPresent()) {
            utenteDao.banUserByEmail(email);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean unbanUser(String email) {
        Optional<Utente> utenteOptional = utenteDao.findByCredenzialiEmail(email);
        if (utenteOptional.isPresent()) {
            utenteDao.unbanUserByEmail(email);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean makeAdmin(String email) {
        Optional<Utente> utenteOptional = utenteDao.findByCredenzialiEmail(email);
        if (utenteOptional.isPresent()) {
            utenteDao.makeAdminByEmail(email);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean revokeAdmin(String email) {
        Optional<Utente> utenteOptional = utenteDao.findByCredenzialiEmail(email);
        if (utenteOptional.isPresent()) {
            utenteDao.revokeAdminByEmail(email);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean checkBan(String email) {
        Optional<Utente> utenteOptional = utenteDao.findByCredenzialiEmail(email);
        if (utenteOptional.isPresent()) {
            return utenteOptional.get().getBannato();
        } else {
            return false;
        }
    }

    @Override
    public List<UtenteDto> searchUsers(String keyword) {
        List<Utente> users = utenteDao.findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCaseOrCredenzialiEmailContainingIgnoreCase(keyword, keyword, keyword);
        return users.stream()
                .map(user -> modelMapper.map(user, UtenteDto.class))
                .toList();
    }


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Utente> utente = utenteDao.findByCredenzialiEmail(email);
        if(utente.isPresent()){
            Utente user = utente.get();
            List<SimpleGrantedAuthority> authorities;
            if(user.getIsAdmin()){
                authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else {
                authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
            }
            return new User(
                    user.getCredenziali().getEmail(),
                    user.getCredenziali().getPassword(),
                    authorities
            );
        }
        throw new UsernameNotFoundException("User not found");
    }





}
