package com.ucelebi.automobile.service;

import com.ucelebi.automobile.model.Image;

import java.util.List;

public interface ImageService extends AbstractService<Image, Long> {
    List<Image> findAllByPartnerUid(String partnerUid);
}
