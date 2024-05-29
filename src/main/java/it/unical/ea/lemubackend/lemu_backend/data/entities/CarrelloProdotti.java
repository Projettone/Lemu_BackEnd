package it.unical.ea.lemubackend.lemu_backend.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="carrelloprodotti")
@Data
@NoArgsConstructor
public class CarrelloProdotti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrello_id", referencedColumnName = "id")
    private Carrello carrello;

    @ManyToOne
    @JoinColumn(name = "prodotto_id", referencedColumnName = "id")
    private Prodotto prodotto;

    @Column(name = "quantita", nullable = false)
    private Integer quantita;
}


