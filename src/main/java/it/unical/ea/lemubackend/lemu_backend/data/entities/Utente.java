package it.unical.ea.lemubackend.lemu_backend.data.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// Entity perchè è un'entità del database e il database di SpringBoot crea/aggiorna in maniera dinamica le entities.
@Entity
// Table: specifica la creazione di una tabella con nome articolo.
@Table(name="utente")
// Data: @Data is like having implicit @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor annotations on the class for every field.
@Data
// NoArgsConstructor: Va a creare un costruttore senza argomenti.
@NoArgsConstructor
public class Utente {

    // ID: Genera un ID univoco per ogni oggetto e lo setta come chiave primaria
    @Id
    // GeneratedValue: Specifica la strategia di generazione dell'ID, in questo caso IDENTITY: il valore viene auto-incrementato per ogni creazione.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Column: crea una colonna dal nome titolo.
    @Column(name="id")
    private Long id;

    @Column(name="nome")
    private String nome;

    @Column(name="cognome")
    private String cognome;

    @Column(name = "admin")
    private Boolean isAdmin;

    @Column(name = "bannato")
    private Boolean bannato;


    // Lob: Large OBject, specifica che la colonna salva dati di grandi dimensioni, in questo caso ArrayList<String> per le immagini.
    @Lob
    @Column(name = "immagineProfilo")
    private String immagineProfilo;

    //@Embedded: Indica che indirizzo è un tipo di oggetto incorporato. Ciò significa che la sua struttura interna viene mappata direttamente alle colonne della tabella del database, anziché utilizzare una relazione separata
    @Embedded
    //@AttributeOverrides Viene utilizzata per sovrascrivere le mappature predefinite tra i campi dell'oggetto incorporato e le colonne del database
    @AttributeOverrides({
            //@AttributeOverride specifica una mappatura personalizzata per un singolo attributo
            @AttributeOverride(name = "via", column = @Column(name = "via_utente")),
            @AttributeOverride(name = "numero_civico", column = @Column(name = "numeroCivico_utente")),
            @AttributeOverride(name = "citta", column = @Column(name = "citta_utente"))
    })
    private Indirizzo indirizzo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "email", column = @Column(name = "email", unique = true)),
            @AttributeOverride(name = "password", column = @Column(name = "password"))
    })
    private Credenziali credenziali;

    //Mapping carrello
    @JsonManagedReference
    @OneToOne(mappedBy = "utente", fetch = FetchType.LAZY)
    private Carrello carrello;

    //Mapping wishlist
    @JsonManagedReference
    @OneToOne(mappedBy = "utente", fetch = FetchType.LAZY)
    private Wishlist wishlist;

    //Mapping recensioni
    @JsonManagedReference
    @OneToMany(mappedBy = "autore", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Recensione> recensione;

    //Mapping ordini
    @JsonManagedReference
    @OneToMany(mappedBy = "utente", fetch = FetchType.LAZY)
    private List<Ordine> ordini;

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", isAdmin=" + isAdmin +
                ", bannato=" + bannato +
                ", immagineProfilo='" + immagineProfilo + '\'' +
                ", indirizzo=" + indirizzo +
                ", credenziali=" + credenziali +
                ", carrello=" + (carrello != null ? carrello.getId() : "null") +
                ", wishlist=" + (wishlist != null ? wishlist.getId() : "null") +
                '}';
    }


    public Utente(String email, String password, String firstName, String lastName) {
        this.credenziali = new Credenziali();
        this.credenziali.setEmail(email);
        this.credenziali.setPassword(password);
        this.nome = firstName;
        this.cognome = lastName;
    }

    
}
