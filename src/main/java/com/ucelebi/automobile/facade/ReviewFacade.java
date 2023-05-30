package com.ucelebi.automobile.facade;

import com.ucelebi.automobile.dto.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewFacade {
    ReviewDTO save(ReviewDTO entity);
    List<ReviewDTO> findAll();
    Page<ReviewDTO> findAll(Pageable pageable);
    List<ReviewDTO> findAllByPartnerUid(String partnerUid);
    List<ReviewDTO> findAllByCustomerUid(String customerUid);
    ReviewDTO update(ReviewDTO entity);
    void delete(ReviewDTO entity);
}
