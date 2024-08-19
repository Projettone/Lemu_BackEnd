package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.data.service.CategoriaService;
import it.unical.ea.lemubackend.lemu_backend.dto.CategoriaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/categoria-api")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/get/{id}")
    public ResponseEntity<CategoriaDto> getCategoriaById(@PathVariable Long id) {
        Optional<CategoriaDto> categoriaDto = categoriaService.getCategoriaById(id);
        return categoriaDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
