package it.unical.ea.lemubackend.lemu_backend.data.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.nimbusds.jose.JOSEException;
import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Credenziali;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteDto;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteRegistrazioneDto;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


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

            System.out.println("User ID: " + userId);
            System.out.println("Email: " + email);
            System.out.println("Email Verified: " + emailVerified);
            System.out.println("Name: " + name);
            System.out.println("Picture URL: " + pictureUrl);
            System.out.println("Locale: " + locale);
            System.out.println("Family Name: " + familyName);
            System.out.println("Given Name: " + givenName);


            Optional<Utente> existingUser = utenteDao.findByCredenzialiEmail(email);
            if (existingUser.isEmpty()) {
                Utente utente = new Utente();
                utente.setNome(givenName);
                utente.setCognome(familyName);
                Credenziali c = new Credenziali(email, "");
                utente.setCredenziali(c);
                utente.setIsAdmin(false);

                utenteDao.save(utente);
            }

            String jwtToken = tokenStore.createToken(Map.of("email", email));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + jwtToken);
            return ResponseEntity.ok().headers(headers).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed authentication");
    }




    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if("admin".equals(email))
            return new User(email, new BCryptPasswordEncoder(12).encode("strongPassword"), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        Optional<Utente> utente = utenteDao.findByCredenzialiEmail(email);
        if(utente.isPresent()) return new User(email, utente.get().getCredenziali().getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        throw new UsernameNotFoundException("User not found");
    }




}
