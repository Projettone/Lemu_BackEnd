package it.unical.ea.lemubackend.lemu_backend.data.service;

import com.nimbusds.jose.JOSEException;
import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.dao.RecensioneDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Recensione;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.RecensioneDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecensioneServiceImpl implements RecensioneService{

    private final TokenStore tokenStore;
    private final RecensioneDao recensioneDao;
    private final ModelMapper modelMapper;


    @Override
    public void save(Recensione recensione) {

    }


    @Override
    public RecensioneDto getById(Long id) {
        Recensione recensione = recensioneDao.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Nessuna recensione corrispondente a id: [%s]", id)));
        return modelMapper.map(recensione, RecensioneDto.class);

    }

    @Override
    public Collection<RecensioneDto> findAll() {
        return recensioneDao.findAll().stream()
                .map(recensione -> modelMapper.map(recensione, RecensioneDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RecensioneDto> findAllByIdProdotto(Long idProdotto) {
        List<Recensione> recensioni = recensioneDao.findAllByProdottoId(idProdotto);
        return recensioni.stream()
                .map(recensione -> modelMapper.map(recensione, RecensioneDto.class))
                .toList();
    }

    @Override
    public ResponseEntity<Page<RecensioneDto>> findAllByUtente(Utente utente, Pageable pageable) {
        Page<Recensione> pagedRecensioni = recensioneDao.findAllByAutore(utente, pageable);
        Page<RecensioneDto> pagedRecensioniDto = pagedRecensioni.map(recensione -> modelMapper.map(recensione, RecensioneDto.class));
        return ResponseEntity.ok(pagedRecensioniDto);
    }

    @Override
    public Boolean deleteReview(String token, Long id) throws ParseException, JOSEException {
        Optional<Utente> utenteOptional = tokenStore.getUser(token);
        if (utenteOptional.isPresent()) {
            Utente utente = utenteOptional.get();
            Optional<Recensione> recensioneOptional = recensioneDao.findById(id);
            if (recensioneOptional.isPresent()) {
                Recensione recensione = recensioneOptional.get();
                if (recensione.getAutore().getId().equals(utente.getId())) {
                    recensioneDao.delete(recensione);
                    return true;
                } else {
                    throw new SecurityException("Utente non autorizzato a cancellare questa recensione");
                }
            }
            return false;
        }
        throw new SecurityException("Token non valido o utente non trovato");
    }


}
