package it.unical.ea.lemubackend.lemu_backend.data.service;

import it.unical.ea.lemubackend.lemu_backend.data.dao.CategoriaDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Categoria;
import it.unical.ea.lemubackend.lemu_backend.dto.CategoriaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaServiceImpl implements  CategoriaService{

    @Autowired
    private CategoriaDao categoriaDao;

    @Override
    public Optional<CategoriaDto> getCategoriaById(Long id) {
        Optional<Categoria> categoria = categoriaDao.findById(id);
        return categoria.map(this::convertToDto);
    }

    private CategoriaDto convertToDto(Categoria categoria) {
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(categoria.getId());
        categoriaDto.setNome(categoria.getNome());
        categoriaDto.setImmagine(categoria.getImmagine());
        return categoriaDto;
    }
}
