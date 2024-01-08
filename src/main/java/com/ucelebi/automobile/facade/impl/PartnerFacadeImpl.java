package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.PartnerDTO;
import com.ucelebi.automobile.dto.PartnerListDTO;
import com.ucelebi.automobile.dto.PartnerUpdateDTO;
import com.ucelebi.automobile.facade.PartnerFacade;
import com.ucelebi.automobile.model.Partner;
import com.ucelebi.automobile.modelFilter.PartnerFilter;
import com.ucelebi.automobile.service.PartnerService;
import com.ucelebi.automobile.util.FileUploadUtil;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class PartnerFacadeImpl implements PartnerFacade {
    public static Logger log = Logger.getLogger(PartnerFacadeImpl.class);
    private final PartnerService partnerService;
    private final ModelMapper modelMapper;

    @Autowired
    public PartnerFacadeImpl(PartnerService partnerService, ModelMapper modelMapper) {
        this.partnerService = partnerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public PartnerDTO save(PartnerDTO entity) {
        Partner partner = modelMapper.map(entity, Partner.class);
        Partner savedPartner = partnerService.save(partner);
        return modelMapper.map(savedPartner, PartnerDTO.class);
    }

    @Override
    public List<PartnerListDTO> findAll() {
        return partnerService.findAll().stream().map(p -> modelMapper.map(p, PartnerListDTO.class)).toList();
    }

    @Override
    public Page<PartnerListDTO> findAll(Pageable pageable, PartnerFilter partnerFilter) {
        if (partnerFilter.getSectorCode() != null && !partnerFilter.getSectorCode().trim().isEmpty()) {
            return findAllBySectors(partnerFilter.getLatitude(), partnerFilter.getLongitude(),partnerFilter.getSectorCode(), pageable);
        } else if (partnerFilter.getTownCode() != null && !partnerFilter.getTownCode().trim().isEmpty()) {
            return findAllByTown(partnerFilter.getLatitude(), partnerFilter.getLongitude(), partnerFilter.getTownCode(), pageable);
        } else if (partnerFilter.getCityCode() != null && !partnerFilter.getCityCode().trim().isEmpty()) {
            return findAllByCity(partnerFilter.getLatitude(), partnerFilter.getLongitude(), partnerFilter.getCityCode(), pageable);
        }
        return partnerService.findAllByDistance(partnerFilter.getLatitude(), partnerFilter.getLongitude(), pageable);
    }

    @Override
    public PartnerDTO update(PartnerUpdateDTO entity) {
        Optional<Partner> partnerOptional = partnerService.findByUid(entity.getUid());
        if (partnerOptional.isEmpty()) return null;
        Partner partner = partnerOptional.get();
        partner.setActive(entity.isActive());
        partner.setName(entity.getName());
        partner.setDisplayName(entity.getDisplayName());
        partner.setPhoneNumber(entity.getPhoneNumber());
        partner.setProfilePhoto(entity.getProfilePhoto());
        partner.setLongitude(entity.getLongitude());
        partner.setLatitude(entity.getLatitude());
        // TODO partner.setWorkingTimes(entity.getOpeningTimes());
        partner.setSundayOpen(entity.isSundayOpen());

        Partner updatedService = partnerService.update(partner);
        return modelMapper.map(updatedService, PartnerDTO.class);
    }

    @Override
    public Page<PartnerListDTO> findAllBySectors(double latitude, double longitude, String sectorCode, Pageable pageable) {
        return partnerService.findAllBySectors(latitude, longitude, sectorCode, pageable);
    }

    @Override
    public Page<PartnerListDTO> findAllByCity(double latitude, double longitude, String cityCode, Pageable pageable) {
        return partnerService.findAllByCity(latitude, longitude, cityCode, pageable);
    }

    @Override
    public Page<PartnerListDTO> findAllByTown(double latitude, double longitude, String townCode, Pageable pageable) {
        return partnerService.findAllByTown(latitude, longitude, townCode, pageable);
    }

    @Override
    public PartnerDTO findByUid(String uid) {
        Optional<Partner> partnerOptional = partnerService.findByUid(uid);
        if (partnerOptional.isEmpty()) return null;
        Partner partner = partnerOptional.get();
        return modelMapper.map(partner, PartnerDTO.class);
    }

    @Override
    public PartnerDTO addProfilePhoto(String uid, MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) return null;

        Optional<Partner> partnerOptional = partnerService.findByUid(uid);

        if (partnerOptional.isEmpty()) return null;

        Partner partner = partnerOptional.get();
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            String uploadDir = "profile-photo/" + uid;
            boolean isSaved = FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            assert isSaved;
            partner.setProfilePhoto(fileName);
            partnerService.update(partner);
        } catch (IOException | RuntimeException e) {
            log.error("Error while adding the profile photo");
            return null;
        }
        return modelMapper.map(partner, PartnerDTO.class);
    }

    @Override
    public void deleteByUid(String uid) {
        try {
            partnerService.deleteByUid(uid);
        } catch (RuntimeException e) {
            log.error("Error while deleting partner.", e);
        }
    }

    @Override
    public void delete(PartnerDTO entity) {
        Optional<Partner> partnerOptional = partnerService.findByUid(entity.getUid());
        if (partnerOptional.isEmpty()) return;
        Partner partner = partnerOptional.get();
        try {
            partnerService.delete(partner);
        } catch (RuntimeException e) {
            log.error("Error while deleting partner.", e);
        }
    }
}
