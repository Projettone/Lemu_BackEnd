package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteDto;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteRegistrazioneDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService{
    private final UtenteDao utenteDao;
    private final ModelMapper modelMapper;

    @Override
    public void save(Utente utente) { utenteDao.save(utente); }
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


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if("admin".equals(email))
            return new User(email, new BCryptPasswordEncoder(12).encode("strongPassword"), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        Optional<Utente> utente = utenteDao.findByCredenzialiEmail(email);
        if(utente.isPresent()) return new User(email, utente.get().getCredenziali().getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        throw new UsernameNotFoundException("User not found");
    }


}
