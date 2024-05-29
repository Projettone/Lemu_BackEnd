package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.dto.CategoriaDto;
import it.unical.ea.lemubackend.lemu_backend.data.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> getCategoriaById(@PathVariable Long id) {
        Optional<CategoriaDto> categoriaDto = categoriaService.getCategoriaById(id);
        return categoriaDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> getAllCategorie() {
        List<CategoriaDto> categorie = categoriaService.getAllCategorie();
        return ResponseEntity.ok(categorie);
    }
}
