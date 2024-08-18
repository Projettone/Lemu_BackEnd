package it.unical.ea.lemubackend.lemu_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class OrdineDto {

    private Long id;

    private String indirizzo;

    private Long idutente;

    private LocalDate dataAcquisto;

    private Double prezzoTotaleOrdine;

    private List<OrdineProdottoDto> ordineProdotti;

}
