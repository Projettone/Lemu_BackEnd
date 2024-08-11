package it.unical.ea.lemubackend.lemu_backend.controller;

import it.unical.ea.lemubackend.lemu_backend.data.service.CouponService;
import it.unical.ea.lemubackend.lemu_backend.dto.CouponDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path="/coupon-api")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/crea-coupon/{valore}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> creaCoupon(@PathVariable Double valore) {
        return couponService.creaCoupon(valore);
    }


    @PostMapping("/riscatta/{couponCode}")
    public ResponseEntity<?> riscattaCoupon(HttpServletRequest request, @PathVariable String couponCode) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                return couponService.riscattaCoupon(token, couponCode);
            } else {
                return new ResponseEntity<>("Authorization header missing or invalid", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paged-coupons")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<CouponDto>> getPagedCoupons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return couponService.getPagedCoupons(pageable);

    }

}
