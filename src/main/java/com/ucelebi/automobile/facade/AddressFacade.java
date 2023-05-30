package com.ucelebi.automobile.facade;

import com.ucelebi.automobile.dto.AddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AddressFacade {
    AddressDTO save(AddressDTO entity);
    List<AddressDTO> findAll();
    Page<AddressDTO> findAll(Pageable pageable);
    Optional<AddressDTO> findByCode(String code);
    AddressDTO update(AddressDTO entity);
    void delete(AddressDTO entity);
}
