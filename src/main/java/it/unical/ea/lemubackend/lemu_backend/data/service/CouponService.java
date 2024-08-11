package it.unical.ea.lemubackend.lemu_backend.data.service;

import com.nimbusds.jose.JOSEException;
import it.unical.ea.lemubackend.lemu_backend.dto.CouponDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.List;

public interface CouponService {

    ResponseEntity<?> creaCoupon(Double Valore);

    ResponseEntity<?> riscattaCoupon(String token, String couponCode) throws ParseException, JOSEException;

    ResponseEntity<Page<CouponDto>> getPagedCoupons(Pageable pageable);


}
