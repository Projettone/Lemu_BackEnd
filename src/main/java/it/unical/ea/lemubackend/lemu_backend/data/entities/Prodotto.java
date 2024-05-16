package it.unical.ea.lemubackend.lemu_backend.data.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name="prodotto")
@Data
@NoArgsConstructor
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="nome")
    private String nome;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "prezzo")
    private Float prezzo;

    @Column(name = "venduti")
    private int venduti;

    @Column(name = "categoria")
    private String categoria;

    @Lob
    @Column(name = "immagineProdotto")
    private String immagineProdotto;

    @OneToMany(mappedBy = "prodotto", fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recensione")
    private List<Recensione> recensione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utente")
    private Utente utente;

    @Column(name = "disponibilita")
    private Boolean disponibilita;
}
