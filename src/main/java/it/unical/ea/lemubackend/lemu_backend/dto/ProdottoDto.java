package it.unical.ea.lemubackend.lemu_backend.dto;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Recensione;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.data.service.ProdottoService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class ProdottoDto{

    private Long id;

    private String nome;

    private String descrizione;

    private Float prezzo;

    private int venduti;

    private String categoria;

    private String immagineProdotto;

    private double valutazione;

    private int numeroRecensioni;

    private List<Recensione> recensione;

    private Utente utente;

    private int disponibilita;


}
