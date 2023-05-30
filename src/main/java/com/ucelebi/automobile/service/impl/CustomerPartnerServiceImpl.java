package com.ucelebi.automobile.service.impl;

import com.ucelebi.automobile.dao.CustomerPartnerDao;
import com.ucelebi.automobile.model.CustomerPartner;
import com.ucelebi.automobile.service.CustomerPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerPartnerServiceImpl extends AbstractServiceImpl<CustomerPartner, Long> implements CustomerPartnerService {

    private final CustomerPartnerDao customerPartnerDao;

    @Autowired
    public CustomerPartnerServiceImpl(CustomerPartnerDao customerPartnerDao) {
        super(customerPartnerDao);
        this.customerPartnerDao = customerPartnerDao;
    }

    @Override
    public Optional<CustomerPartner> isPartnerExistInFavorite(String customerUid, String partnerUid) {
        return customerPartnerDao.findByCustomer_UidAndPartner_Uid(customerUid, partnerUid);
    }
}
