package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.model.CustomerPartner;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerPartnerDao extends AbstractItemDao<CustomerPartner, Long> {
    Optional<CustomerPartner> findByCustomer_UidAndPartner_Uid(String customerUid, String partnerUid);
}
