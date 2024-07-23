package it.unical.ea.lemubackend.lemu_backend.data.service;

import com.nimbusds.jose.JOSEException;
import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Credenziali;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteDto;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteLoginDto;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteRegistrazioneDto;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class UtenteServiceImpl implements UtenteService, UserDetailsService {
    private final UtenteDao utenteDao;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenStore tokenStore;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UtenteServiceImpl(UtenteDao utenteDao, ModelMapper modelMapper, @Lazy AuthenticationManager authenticationManager, TokenStore tokenStore, PasswordEncoder passwordEncoder) {
        this.utenteDao = utenteDao;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
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


    /*
    @Override
    public UtenteRegistrazioneDto googleAuthentication(Model model,
                                                       @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                                       @AuthenticationPrincipal OAuth2User oauth2User) {
        model.addAttribute("userName", oauth2User.getName());
        model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
        model.addAttribute("userAttributes", oauth2User.getAttributes());

        String email = oauth2User.getAttributes().get("email").toString();
        System.out.println("ATRTIBUTI:" + oauth2User.getAttributes());

        Optional<Utente> optionalUtente = utenteDao.findByCredenzialiEmail(email);

        if(optionalUtente.isEmpty()) {
            optionalUtente = Optional.of(new Utente(email,
                    oauth2User.getAttributes().get("given_name").toString(),
                    oauth2User.getAttributes().get("family_name").toString()));
            utenteDao.save(optionalUtente.get());
        }
        return modelMapper.map(optionalUtente, UtenteRegistrazioneDto.class);

    }

     */


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if("admin".equals(email))
            return new User(email, new BCryptPasswordEncoder(12).encode("strongPassword"), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        Optional<Utente> utente = utenteDao.findByCredenzialiEmail(email);
        if(utente.isPresent()) return new User(email, utente.get().getCredenziali().getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        throw new UsernameNotFoundException("User not found");
    }




}
