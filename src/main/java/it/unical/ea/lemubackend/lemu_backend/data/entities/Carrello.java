package it.unical.ea.lemubackend.lemu_backend.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

//questa classe è un'entità del database
@Entity
//tabella con nome carrello
@Table(name="carrello")
//genera automaticamente getter, setter, toString, equals, hashCode e un costruttore richiesto per ogni campo
@Data
//genera un costruttore senza argomenti.
@NoArgsConstructor
public class Carrello {

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

    //indica che la colonna salva dati di grandi dimensioni, in questo caso un JSON con ID prodotto e quantità.
    @Lob
    @Column(name = "prodotti_quantita")
    // usiamo map per memorizzare gli ID prodotto e le quantità.
    private Map<Long, Integer> prodottiQuantita;

}
