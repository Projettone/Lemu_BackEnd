package it.unical.ea.lemubackend.lemu_backend.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "wishlist")
@NoArgsConstructor
public class Wishlist {

    @Id
    @Column(name="id")
    private Long id;
}
