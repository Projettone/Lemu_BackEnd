package it.unical.ea.lemubackend.lemu_backend.data.dao;

import it.unical.ea.lemubackend.lemu_backend.data.entities.Coupon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponDao extends JpaRepository<Coupon, Long>, JpaSpecificationExecutor<Coupon> {

    @Transactional
    @Modifying
    @Query("UPDATE Coupon c SET c.valido = false WHERE c.codice = :codice")
    void invalidateCouponByCode(String codice);

    List<Coupon> getAllByValido(Boolean valido);

    Optional<Coupon> findCouponByCodice(String codice);
    Page<Coupon> findAllByValidoTrue(Pageable pageable);
}
