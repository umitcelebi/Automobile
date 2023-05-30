package com.ucelebi.automobile.service;

import com.ucelebi.automobile.model.Review;

import java.util.List;

public interface ReviewService extends AbstractService<Review, Long> {
    List<Review> findAllByPartnerUid(String partnerUid);
    List<Review> findAllByCustomerUid(String customerUid);
}
