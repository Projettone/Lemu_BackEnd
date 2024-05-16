package it.unical.ea.lemubackend.lemu_backend.data.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "ordine")
@NoArgsConstructor
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "indirizzo")
    private Indirizzo indirizzo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acquirente")
    private Utente acquirente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venditore")
    private Utente venditore;

    @Column(name = "data_acquisto")
    private LocalDate dataAcquisto;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "prodotto")
    private List<Prodotto> prodotti;

}
