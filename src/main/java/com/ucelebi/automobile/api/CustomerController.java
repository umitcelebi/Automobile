package com.ucelebi.automobile.api;

import com.ucelebi.automobile.dto.CustomerDTO;
import com.ucelebi.automobile.dto.CustomerListDTO;
import com.ucelebi.automobile.facade.CustomerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/customers")
@RestController
public class CustomerController {

    private final CustomerFacade customerFacade;

    @Autowired
    public CustomerController(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    @GetMapping
    public ResponseEntity<Page<CustomerListDTO>> getCustomerList(@RequestParam(value = "page",defaultValue = "0") int page,
                                                                 @RequestParam(value = "size", defaultValue = "30") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CustomerListDTO> customerPage = customerFacade.findAll(pageRequest);
        return ResponseEntity.ok().body(customerPage);
    }

    @GetMapping("/details")
    public ResponseEntity<CustomerDTO> getCustomer(@RequestParam String username) {
        CustomerDTO customer = customerFacade.findByUid(username);
        return ResponseEntity.ok().body(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> update(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO savedCustomer = customerFacade.save(customerDTO);
        return ResponseEntity.ok().body(savedCustomer);
    }

}
