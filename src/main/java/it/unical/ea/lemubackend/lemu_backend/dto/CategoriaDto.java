package it.unical.ea.lemubackend.lemu_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaDto {
    private Long id;
    private String nome;
    private String immagine;
}
