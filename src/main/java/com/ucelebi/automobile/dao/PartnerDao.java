package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.dto.PartnerListDTO;
import com.ucelebi.automobile.model.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerDao extends AbstractItemDao<Partner, Long> {

    String distance ="(6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * cos(radians(p.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(p.latitude))))";

    Optional<Partner> findByUid(String uid);

    @Query("SELECT NEW com.ucelebi.automobile.dto.PartnerListDTO(p.creationTime, p.modifiedTime, p.active, p.uid, p.displayName, p.profilePhoto, p.userRating, p.latitude, p.longitude, (6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * cos(radians(p.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(p.latitude))))) FROM Partner as p ORDER BY "+ distance)
    Page<PartnerListDTO> findAllByDistance(@Param("latitude") double latitude, @Param("longitude") double longitude, Pageable pageable);

    @Query("SELECT NEW com.ucelebi.automobile.dto.PartnerListDTO(p.creationTime, p.modifiedTime, p.active, p.uid, p.displayName, p.profilePhoto, p.userRating, p.latitude, p.longitude, (6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * cos(radians(p.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(p.latitude))))) FROM Partner as p LEFT JOIN p.sectors s WHERE s.code=:sectorCode ORDER BY "+ distance)
    Page<PartnerListDTO> findAllBySectorsAndDistance(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("sectorCode") String sectorCode, Pageable pageable);

    @Query("SELECT NEW com.ucelebi.automobile.dto.PartnerListDTO(p.creationTime, p.modifiedTime, p.active, p.uid, p.displayName, p.profilePhoto, p.userRating, p.latitude, p.longitude, (6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * cos(radians(p.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(p.latitude))))) FROM Partner as p LEFT JOIN p.address a JOIN a.city ci WHERE ci.code=:cityCode GROUP BY p.uid ORDER BY "+ distance)
    Page<PartnerListDTO> findByAddress_City_CodeByDistance(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("cityCode") String cityCode, Pageable pageable);

    @Query("SELECT NEW com.ucelebi.automobile.dto.PartnerListDTO(p.creationTime, p.modifiedTime, p.active, p.uid, p.displayName, p.profilePhoto, p.userRating, p.latitude, p.longitude, (6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * cos(radians(p.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(p.latitude))))) FROM Partner as p LEFT JOIN p.address a JOIN a.town to WHERE to.code=:townCode GROUP BY p.uid ORDER BY "+ distance)
    Page<PartnerListDTO> findByAddress_Town_CodeByDistance(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("townCode") String townCode, Pageable pageable);

}
