package com.ucelebi.automobile.api;

import com.ucelebi.automobile.dto.AddressDTO;
import com.ucelebi.automobile.dto.CityDTO;
import com.ucelebi.automobile.dto.CountryDTO;
import com.ucelebi.automobile.dto.TownDTO;
import com.ucelebi.automobile.facade.AddressFacade;
import com.ucelebi.automobile.facade.CityFacade;
import com.ucelebi.automobile.facade.CountryFacade;
import com.ucelebi.automobile.facade.TownFacade;
import com.ucelebi.automobile.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/address")
@RestController
public class AddressController {

    private final CountryFacade countryFacade;
    private final CityFacade cityFacade;
    private final TownFacade townFacade;
    private final AddressFacade addressFacade;

    @Autowired
    public AddressController(CountryFacade countryFacade, CityFacade cityFacade, TownFacade townFacade, AddressFacade addressFacade) {
        this.countryFacade = countryFacade;
        this.cityFacade = cityFacade;
        this.townFacade = townFacade;
        this.addressFacade = addressFacade;
    }

    @GetMapping("/cities")
    public ResponseEntity<List<CityDTO>> getCityList() {
        List<CityDTO> cityList = cityFacade.findAll();
        return ResponseEntity.ok().body(cityList);
    }

    @GetMapping("/city")
    public ResponseEntity<CityDTO> getCity(@RequestParam String code) {
        CityDTO cityByCode = cityFacade.findCityByCode(code);
        return ResponseEntity.ok().body(cityByCode);
    }

    @PostMapping("/city")
    public ResponseEntity<CityDTO> saveCity(@RequestBody CityDTO cityDTO) {
        CityDTO savedCity = cityFacade.save(cityDTO);
        return ResponseEntity.ok().body(savedCity);
    }

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDTO>> getCountryList() {
        List<CountryDTO> countryList = countryFacade.findAll();
        return ResponseEntity.ok().body(countryList);
    }

    @GetMapping("/country")
    public ResponseEntity<CountryDTO> getCountry(@RequestParam String code) {
        CountryDTO countryByCode = countryFacade.findCountryByCode(code);
        return ResponseEntity.ok().body(countryByCode);
    }

    @PostMapping("/country")
    public ResponseEntity<CountryDTO> saveCountry(@RequestBody CountryDTO countryDTO) {
        CountryDTO savedCountry = countryFacade.save(countryDTO);
        return ResponseEntity.ok().body(savedCountry);
    }

    @GetMapping("/towns")
    public ResponseEntity<List<TownDTO>> getTownList(@RequestParam(required = false) String cityCode) {
        List<TownDTO> townList;
        if (cityCode != null && !cityCode.trim().isEmpty()) {
            townList = townFacade.findAllByCityCode(cityCode);
        }else {
            townList = townFacade.findAll();
        }
        return ResponseEntity.ok().body(townList);
    }

    @GetMapping("/town")
    public ResponseEntity<TownDTO> getTown(@RequestParam String code) {
        TownDTO townByCode = townFacade.findTownByCode(code);
        return ResponseEntity.ok().body(townByCode);
    }

    @PostMapping("/town")
    public ResponseEntity<TownDTO> saveTown(@RequestBody TownDTO townDTO) {
        TownDTO savedTown = townFacade.save(townDTO);
        return ResponseEntity.ok().body(savedTown);
    }

    @PostMapping("/address/save")
    public ResponseEntity<Address> saveAddress(@RequestBody AddressDTO addressDTO) {
        Address savedAddress = addressFacade.save(addressDTO);
        return ResponseEntity.ok().body(savedAddress);
    }

}
