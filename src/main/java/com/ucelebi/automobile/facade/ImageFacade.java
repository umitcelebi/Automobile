package com.ucelebi.automobile.facade;

import com.ucelebi.automobile.dto.ImageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ImageFacade {
    ImageDTO save(ImageDTO entity);
    List<ImageDTO> findAll();
    List<ImageDTO> findAllByPartnerUid(String partnerUid);
    Page<ImageDTO> findAll(Pageable pageable);
    void delete(ImageDTO entity);
}
