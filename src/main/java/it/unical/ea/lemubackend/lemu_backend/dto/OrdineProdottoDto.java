package it.unical.ea.lemubackend.lemu_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class OrdineProdottoDto {
    private Long id;
    private Long ordineId;
    private Long prodottoId;
    private int quantita;
}