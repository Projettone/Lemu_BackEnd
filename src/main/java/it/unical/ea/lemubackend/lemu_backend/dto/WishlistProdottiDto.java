package it.unical.ea.lemubackend.lemu_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishlistProdottiDto {
    private Long id;
    private Long wishlistId;
    private Long prodottoId;
}
