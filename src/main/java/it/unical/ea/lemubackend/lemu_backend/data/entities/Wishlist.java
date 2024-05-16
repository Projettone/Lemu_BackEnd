package it.unical.ea.lemubackend.lemu_backend.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

//questa classe è un'entità del database
@Entity
//tabella con nome wishlist
@Table(name="wishlist")
//genera automaticamente getter, setter, toString, equals, hashCode e un costruttore richiesto per ogni campo
@Data
//genera un costruttore senza argomenti.
@NoArgsConstructor
public class Wishlist {

    //imposta un id univoco come chiave primaria.
    @Id
    //specifica la strategia di generazione dell'ID, con identity il valore viene autoincrementato ogni creazione
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //crea una colonna con il nome id.
    @Column(name="id")
    private Long id;

    //indica una relazione uno a uno
    @OneToOne
    //specifica la colonna che mappa l'id dell'utente collegato
    @JoinColumn(name = "utente_id", referencedColumnName = "id")
    private Utente utente;

    //indica che la colonna salva dati di grandi dimensioni, in questo caso un JSON con gli ID dei prodotti.
    @Lob
    @Column(name = "prodotti")
    // usiamo list per memorizzare gli ID dei prodotti.
    private List<Long> prodotti;
}

