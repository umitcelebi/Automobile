package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.model.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewDao extends AbstractItemDao<Review, Long> {
    List<Review> findAllByCustomer_Uid(String uid);
    List<Review> findAllByPartner_Uid(String uid);
}
