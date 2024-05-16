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

    private String commento;

    @NotNull
    private String autore;

    @NotNull
    private String idProdotto;

}
