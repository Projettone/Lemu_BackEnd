package it.unical.ea.lemubackend.lemu_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WishlistDto {
    private Long id;
    private Long utenteId;
    private List<Long> prodotti;
}
