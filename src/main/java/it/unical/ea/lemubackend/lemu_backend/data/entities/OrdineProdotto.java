package it.unical.ea.lemubackend.lemu_backend.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "ordineProdotto")
@NoArgsConstructor
public class OrdineProdotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdOrdine")
    private Ordine ordine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdProdotto")
    private Prodotto prodotto;

    @Column(name = "quantità")
    private int quantita;

}
