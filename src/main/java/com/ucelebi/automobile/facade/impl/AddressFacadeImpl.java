package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.AddressDTO;
import com.ucelebi.automobile.facade.AddressFacade;
import com.ucelebi.automobile.model.Address;
import com.ucelebi.automobile.model.City;
import com.ucelebi.automobile.model.Country;
import com.ucelebi.automobile.model.Town;
import com.ucelebi.automobile.service.AddressService;
import com.ucelebi.automobile.service.CityService;
import com.ucelebi.automobile.service.CountryService;
import com.ucelebi.automobile.service.TownService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AddressFacadeImpl implements AddressFacade {

    private final AddressService addressService;
    private final CountryService countryService;
    private final CityService cityService;
    private final TownService townService;
    private final ModelMapper modelMapper;

    public static Logger log = Logger.getLogger(AddressFacadeImpl.class);

    @Autowired
    public AddressFacadeImpl(AddressService addressService, CountryService countryService, CityService cityService, TownService townService, ModelMapper modelMapper) {
        this.addressService = addressService;
        this.countryService = countryService;
        this.cityService = cityService;
        this.townService = townService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Address save(AddressDTO entity) {
        Address address = modelMapper.map(entity, Address.class);
        address.setCode(String.valueOf(System.currentTimeMillis()));

        Optional<Country> countryByCode = countryService.findCountryByCode(entity.getCountryCode());
        Optional<City> cityByCode = cityService.findCityByCode(entity.getCityCode());
        Optional<Town> townByCode = townService.findByCode(entity.getTownCode());

        address.setCity(cityByCode.orElse(null));
        address.setCountry(countryByCode.orElse(null));
        address.setTown(townByCode.orElse(null));

        return addressService.save(address);
    }

    @Override
    public List<AddressDTO> findAll() {
        return addressService.findAll().stream().map(address -> modelMapper.map(address, AddressDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Page<AddressDTO> findAll(Pageable pageable) {
        Page<Address> getByPageable = addressService.findAll(pageable);
        List<AddressDTO> addressDTOS = getByPageable.getContent().stream().map(address -> modelMapper.map(address, AddressDTO.class)).toList();
        return new PageImpl<>(addressDTOS,pageable,addressDTOS.size());
    }

    @Override
    public Optional<AddressDTO> findByCode(String code) {
        Optional<Address> optionalAddress = addressService.findByCode(code);
        if (optionalAddress.isEmpty()){
            return Optional.empty();
        }
        AddressDTO addressDTO = modelMapper.map(optionalAddress.get(), AddressDTO.class);
        return Optional.of(addressDTO);
    }

    @Override
    public AddressDTO update(AddressDTO entity) {
        Optional<Address> addressOptional = addressService.findByCode(entity.getCode());
        if (addressOptional.isEmpty()) {
            throw new IllegalStateException("Guncellenecek adres bilgisi bulunamadi.");
        }
        Optional<Country> countryByCode = countryService.findCountryByCode(entity.getCountryCode());
        Optional<City> cityByCode = cityService.findCityByCode(entity.getCityCode());
        Optional<Town> townByCode = townService.findByCode(entity.getTownCode());

        Address address = addressOptional.get();

        Country country = countryByCode.orElse(null);
        City city = cityByCode.orElse(null);
        Town town = townByCode.orElse(null);

        address.setCity(city);
        address.setCountry(country);
        address.setTown(town);
        address.setPostalCode(entity.getPostalCode());
        address.setStreetName(entity.getStreetName());
        address.setStreetNumber(entity.getStreetNumber());
        address.setLine(entity.getLine());
        Address updatedAddress = addressService.update(address);
        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    @Override
    public void delete(AddressDTO entity) {
        Optional<Address> addressOptional = addressService.findByCode(entity.getCode());
        if (addressOptional.isEmpty()) {
            return;
        }
        Address address = addressOptional.get();
        try {
            addressService.delete(address);
        } catch (RuntimeException e) {
            log.error("Error while deleting address.",e);
        }
    }
}
