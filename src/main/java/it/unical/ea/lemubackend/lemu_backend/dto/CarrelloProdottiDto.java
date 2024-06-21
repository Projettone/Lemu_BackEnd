package it.unical.ea.lemubackend.lemu_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarrelloProdottiDto {
    private Long id;
    private Long carrelloId;
    private Long prodottoId;
    private Integer quantita;
}

