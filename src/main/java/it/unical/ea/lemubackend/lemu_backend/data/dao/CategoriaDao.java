package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaDao extends JpaRepository<Categoria, Long> {
}
