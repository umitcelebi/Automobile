package com.ucelebi.automobile.service.impl;

import com.ucelebi.automobile.dao.ImageDao;
import com.ucelebi.automobile.model.Image;
import com.ucelebi.automobile.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl extends AbstractServiceImpl<Image, Long> implements ImageService {

    private final ImageDao imageDao;

    @Autowired
    public ImageServiceImpl(ImageDao imageDao) {
        super(imageDao);
        this.imageDao = imageDao;
    }

    @Override
    public List<Image> findAllByPartnerUid(String partnerUid) {
        return imageDao.findAllByPartner_Uid(partnerUid);
    }
}
