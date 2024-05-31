package it.unical.ea.lemubackend.lemu_backend.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@RequiredArgsConstructor
public class UtenteRegistrazioneDto {
    @NotNull
    private String nome;
    @NotNull
    private String cognome;
    @Email
    private String credenzialiEmail;
}
