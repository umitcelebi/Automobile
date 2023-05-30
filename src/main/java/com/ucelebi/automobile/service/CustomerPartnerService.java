package com.ucelebi.automobile.service;

import com.ucelebi.automobile.model.CustomerPartner;

import java.util.Optional;

public interface CustomerPartnerService extends AbstractService<CustomerPartner, Long> {
    Optional<CustomerPartner> isPartnerExistInFavorite(String customerUid,String partnerUid);
}
