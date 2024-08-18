package it.unical.ea.lemubackend.lemu_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@ToString
public class RecensioneDto {

    private Long id;
    @NotNull
    private Float rating;

    @NotNull
    private String commento;

    @NotNull
    private String nomeProdotto;

    @NotNull
    private Long prodottoId;

    @NotNull
    private String credenzialiEmailAutore;

    private String immagineProfiloAutore;
}
