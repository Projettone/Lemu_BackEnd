package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.CarrelloProdotti;
import it.unical.ea.lemubackend.lemu_backend.dto.CarrelloProdottiDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrelloProdottiDao extends JpaRepository<CarrelloProdotti, Long> {

    List<CarrelloProdotti> findAllByCarrello_Id(Long carrello_id);
}
