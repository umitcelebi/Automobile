package com.ucelebi.automobile.api;

import com.ucelebi.automobile.dto.PartnerUpdateDTO;
import com.ucelebi.automobile.modelFilter.PartnerFilter;
import com.ucelebi.automobile.dto.PartnerDTO;
import com.ucelebi.automobile.dto.PartnerListDTO;
import com.ucelebi.automobile.facade.PartnerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/partners")
@RestController
public class PartnerController {

    private final PartnerFacade partnerFacade;

    @Autowired
    public PartnerController(PartnerFacade partnerFacade) {
        this.partnerFacade = partnerFacade;
    }

    @GetMapping
    public ResponseEntity<Page<PartnerListDTO>> getPartnerList(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                @RequestParam(value = "size", defaultValue = "30", required = false) int size,
                                                                @RequestParam(value = "latitude", defaultValue = "41.0784957", required = false) double latitude,
                                                                @RequestParam(value = "longitude", defaultValue = "28.8097494", required = false) double longitude,
                                                                @RequestParam(value = "sector", defaultValue = "", required = false) String sector,
                                                                @RequestParam(value = "city", defaultValue = "", required = false) String city,
                                                                @RequestParam(value = "town", defaultValue = "", required = false) String town) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PartnerListDTO> partnerPage = partnerFacade.findAll(pageRequest, new PartnerFilter(sector,city,town, latitude, longitude));
        return ResponseEntity.ok().body(partnerPage);
    }

    @GetMapping("/details")
    public ResponseEntity<PartnerDTO> getPartner(@RequestParam String username) {
        PartnerDTO partner = partnerFacade.findByUid(username);
        return ResponseEntity.ok().body(partner);
    }

    @PostMapping("/update")
    public ResponseEntity<PartnerDTO> update(@RequestBody PartnerUpdateDTO partnerDTO) {
        PartnerDTO savedPartner = partnerFacade.update(partnerDTO);
        return ResponseEntity.ok().body(savedPartner);
    }

    @PostMapping("/add-pp")
    public ResponseEntity<String> addProfilePhoto(@RequestParam String uid, @RequestParam("photo") MultipartFile multipartFile) {
        boolean isAdded = partnerFacade.addProfilePhoto(uid, multipartFile);
        if (!isAdded) return ResponseEntity.badRequest().body("Fail");
        return ResponseEntity.ok().body("Success");
    }


}
