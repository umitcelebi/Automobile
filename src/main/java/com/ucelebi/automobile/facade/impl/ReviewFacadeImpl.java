package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.ReviewDTO;
import com.ucelebi.automobile.facade.ReviewFacade;
import com.ucelebi.automobile.model.Review;
import com.ucelebi.automobile.service.ReviewService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewFacadeImpl implements ReviewFacade {

    public static Logger log = Logger.getLogger(PartnerFacadeImpl.class);
    private final ReviewService reviewService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewFacadeImpl(ReviewService reviewService, ModelMapper modelMapper) {
        this.reviewService = reviewService;
        this.modelMapper = modelMapper;
    }


    @Override
    public ReviewDTO save(ReviewDTO entity) {
        Review review = modelMapper.map(entity, Review.class);
        Review savedReview = reviewService.save(review);
        return modelMapper.map(savedReview, ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> findAll() {
        return reviewService.findAll().stream().map(r -> modelMapper.map(r, ReviewDTO.class)).toList();
    }

    @Override
    public Page<ReviewDTO> findAll(Pageable pageable) {
        Page<Review> reviewPage = reviewService.findAll(pageable);
        List<ReviewDTO> reviewList = reviewPage.getContent().stream().map(r -> modelMapper.map(r, ReviewDTO.class)).toList();
        return new PageImpl<>(reviewList, pageable, reviewList.size());
    }

    @Override
    public List<ReviewDTO> findAllByPartnerUid(String partnerUid) {
        return reviewService.findAllByPartnerUid(partnerUid).stream().map(r -> modelMapper.map(r, ReviewDTO.class)).toList();
    }

    @Override
    public List<ReviewDTO> findAllByCustomerUid(String customerUid) {
        return reviewService.findAllByCustomerUid(customerUid).stream().map(r -> modelMapper.map(r, ReviewDTO.class)).toList();
    }

    @Override
    public ReviewDTO update(ReviewDTO entity) {
        return null;
    }

    @Override
    public void delete(ReviewDTO entity) {

    }
}
