package com.ucelebi.automobile.service.impl;

import com.ucelebi.automobile.dao.ReviewDao;
import com.ucelebi.automobile.model.Review;
import com.ucelebi.automobile.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl extends AbstractServiceImpl<Review, Long> implements ReviewService {

    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        super(reviewDao);
        this.reviewDao = reviewDao;
    }

    @Override
    public List<Review> findAllByPartnerUid(String partnerUid) {
        return reviewDao.findAllByPartner_Uid(partnerUid);
    }

    @Override
    public List<Review> findAllByCustomerUid(String customerUid) {
        return reviewDao.findAllByCustomer_Uid(customerUid);
    }
}
