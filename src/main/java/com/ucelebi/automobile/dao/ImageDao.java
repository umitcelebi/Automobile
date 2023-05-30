package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.model.Image;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDao extends AbstractItemDao<Image, Long> {
    List<Image> findAllByPartner_Uid(String partnerUid);
}
