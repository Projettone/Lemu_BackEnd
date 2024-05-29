package it.unical.ea.lemubackend.lemu_backend.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "ordineProdotto")
@NoArgsConstructor
public class OrdineProdotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "indirizzo")
    private Indirizzo indirizzo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acquirente")
    private Utente utente;


    @Column(name = "data_acquisto")
    private LocalDate dataAcquisto;

    @Column(name = "prezzoTotaleOrdine")
    private double prezzoTotaleOrdine;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdineProdotto> ordineProdotti; 


}
