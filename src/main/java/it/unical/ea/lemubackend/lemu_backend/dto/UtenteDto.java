package it.unical.ea.lemubackend.lemu_backend.dto;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Indirizzo;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;


@Data
@NoArgsConstructor
@ToString
public class UtenteDto {

    private Long id;

    @NotNull
    private String nome;
    @NotNull
    private String cognome;

    private Boolean isAdmin;

    private String immagineProfilo;

    private Indirizzo indirizzo;

    private String username; //credenziali

    @Email
    private String email; //credenziali
}
