package it.unical.ea.lemubackend.lemu_backend.data.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Indirizzo {
    private String via;
    private Integer numeroCivico;
    private String citta;
}
