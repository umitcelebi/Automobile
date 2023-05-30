package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.ImageDTO;
import com.ucelebi.automobile.facade.ImageFacade;
import com.ucelebi.automobile.model.Image;
import com.ucelebi.automobile.service.ImageService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImageFacadeImpl implements ImageFacade {
    public static Logger log = Logger.getLogger(ImageFacadeImpl.class);
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    @Autowired
    public ImageFacadeImpl(ImageService imageService, ModelMapper modelMapper) {
        this.imageService = imageService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ImageDTO save(ImageDTO entity) {
        Image image = modelMapper.map(entity, Image.class);
        Image savedImage = imageService.save(image);
        return modelMapper.map(savedImage, ImageDTO.class);
    }

    @Override
    public List<ImageDTO> findAll() {
        return imageService.findAll().stream().map(i -> modelMapper.map(i, ImageDTO.class)).toList();
    }

    @Override
    public List<ImageDTO> findAllByPartnerUid(String partnerUid) {
        return imageService.findAllByPartnerUid(partnerUid).stream().map(i -> modelMapper.map(i, ImageDTO.class)).toList();
    }

    @Override
    public Page<ImageDTO> findAll(Pageable pageable) {
        Page<Image> imagePage = imageService.findAll(pageable);
        List<ImageDTO> imageList = imagePage.getContent().stream().map(i -> modelMapper.map(i, ImageDTO.class)).toList();
        return new PageImpl<>(imageList, pageable, imageList.size());
    }

    @Override
    public void delete(ImageDTO entity) {}
}
