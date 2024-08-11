package it.unical.ea.lemubackend.lemu_backend.data.service;

import com.nimbusds.jose.JOSEException;
import it.unical.ea.lemubackend.lemu_backend.config.security.TokenStore;
import it.unical.ea.lemubackend.lemu_backend.data.dao.CouponDao;
import it.unical.ea.lemubackend.lemu_backend.data.dao.UtenteDao;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Coupon;
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.dto.CouponDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService{

    private final UtenteDao utenteDao;
    private final CouponDao couponDao;
    private final ModelMapper modelMapper;


    @Autowired
    public CouponServiceImpl(UtenteDao utenteDao, CouponDao couponDao, ModelMapper modelMapper) {
        this.utenteDao = utenteDao;
        this.couponDao = couponDao;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<?> creaCoupon(Double valore) {
        String codiceCoupon = generateRandomCode();

        Coupon coupon = new Coupon();
        coupon.setCodice(codiceCoupon);
        coupon.setValore(valore);
        coupon.setValido(true);

        couponDao.save(coupon);
        return new ResponseEntity<>("Coupon creato con successo", HttpStatus.CREATED);
    }

    private String generateRandomCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public ResponseEntity<?> riscattaCoupon(String token, String couponCode) throws ParseException, JOSEException {
        String email = TokenStore.getInstance().getUserEmail(token);
        Optional<Utente> utenteOptional = utenteDao.findByCredenzialiEmail(email);
        Optional<Coupon> couponOptional = couponDao.findCouponByCodice(couponCode);

        if (utenteOptional.isPresent() && couponOptional.isPresent()) {
            Coupon coupon = couponOptional.get();
            if (coupon.getValido()) {
                utenteDao.updateBalanceByEmail(email, coupon.getValore());
                couponDao.invalidateCouponByCode(coupon.getCodice());
                return ResponseEntity.ok("Coupon riscattato con successo");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Coupon non valido o scaduto");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente o coupon non trovati.");
        }
    }

    @Override
    public ResponseEntity<Page<CouponDto>> getPagedCoupons(Pageable pageable) {
        Page<Coupon> pagedCoupons = couponDao.findAll(pageable);
        Page<CouponDto> pagedCouponDtos = pagedCoupons.map(coupon -> modelMapper.map(coupon, CouponDto.class));
        return ResponseEntity.ok(pagedCouponDtos);
    }


}
