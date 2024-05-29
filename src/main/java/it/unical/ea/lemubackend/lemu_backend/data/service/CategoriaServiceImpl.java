package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.CategoriaDao;
import it.unical.ea.lemubackend.lemu_backend.dto.CategoriaDto;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Categoria;
import it.unical.ea.lemubackend.lemu_backend.data.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements  CategoriaService{

    @Autowired
    private CategoriaDao categoriaDao;

    @Override
    public Optional<CategoriaDto> getCategoriaById(Long id) {
        Optional<Categoria> categoria = categoriaDao.findById(id);
        return categoria.map(this::convertToDto);
    }

    @Override
    public List<CategoriaDto> getAllCategorie() {
        List<Categoria> categorie = categoriaDao.findAll();
        return categorie.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private CategoriaDto convertToDto(Categoria categoria) {
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(categoria.getId());
        categoriaDto.setNome(categoria.getNome());
        categoriaDto.setImmagine(categoria.getImmagine());
        return categoriaDto;
    }
}
