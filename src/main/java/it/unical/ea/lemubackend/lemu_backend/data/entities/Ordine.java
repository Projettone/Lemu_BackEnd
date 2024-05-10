package it.unical.ea.lemubackend.lemu_backend.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "ordine")
@NoArgsConstructor
public class Ordine {

    @Id
    @Column(name="id")
    private Long id;

}
