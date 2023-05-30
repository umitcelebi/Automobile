package com.ucelebi.automobile.facade;

import com.ucelebi.automobile.dto.CustomerDTO;
import com.ucelebi.automobile.dto.CustomerListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerFacade {
    CustomerDTO save(CustomerDTO entity);
    List<CustomerListDTO> findAll();
    Page<CustomerListDTO> findAll(Pageable pageable);
    CustomerDTO findByUid(String uid);
    CustomerDTO update(CustomerDTO entity);
    void delete(CustomerDTO entity);
    void deleteByUid(String uid);
}
