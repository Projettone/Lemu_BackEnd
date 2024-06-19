package it.unical.ea.lemubackend.lemu_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UtenteLoginDto {
    @Email
    private String credenzialiEmail;
    @NotNull
    private String credenzialiPassword;
}
