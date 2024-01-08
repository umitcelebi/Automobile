package com.ucelebi.automobile.auth;

import com.ucelebi.automobile.dao.UserDao;
import com.ucelebi.automobile.dto.AddressDTO;
import com.ucelebi.automobile.dto.WorkingTimesDTO;
import com.ucelebi.automobile.enums.Role;
import com.ucelebi.automobile.exception.AlreadyExistException;
import com.ucelebi.automobile.facade.AddressFacade;
import com.ucelebi.automobile.model.Address;
import com.ucelebi.automobile.model.Customer;
import com.ucelebi.automobile.model.Partner;
import com.ucelebi.automobile.model.Sector;
import com.ucelebi.automobile.model.User;
import com.ucelebi.automobile.model.WorkingTimes;
import com.ucelebi.automobile.security.JwtService;
import com.ucelebi.automobile.service.SectorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserDao userDao;
    private final SectorService sectorService;
    private final AddressFacade addressFacade;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthenticationService(UserDao userDao, SectorService sectorService, AddressFacade addressFacade, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.sectorService = sectorService;
        this.addressFacade = addressFacade;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }
    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> byUsername = userDao.findUserByUid(request.getUsername());
        if (byUsername.isPresent()) {
            throw new AlreadyExistException("Kullanıcı sistemde mevcut");
        }

        User user = null;

        if (request.getRole().equals(Role.CUSTOMER)) {
            user = new Customer.builder()
                    .uid(request.getUsername())
                    .name(request.getName())
                    .displayName(request.getName())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phoneNumber(request.getPhoneNumber())
                    .role(request.getRole())
                    .build();

        } else if (request.getRole().equals(Role.PARTNER)) {
            PartnerRegisterRequest partnerRegisterRequest = (PartnerRegisterRequest) request;
            user = new Partner.builder()
                    .uid(partnerRegisterRequest.getUsername())
                    .name(partnerRegisterRequest.getName())
                    .displayName(partnerRegisterRequest.getDisplayName())
                    .password(passwordEncoder.encode(partnerRegisterRequest.getPassword()))
                    .phoneNumber(partnerRegisterRequest.getPhoneNumber())
                    .role(partnerRegisterRequest.getRole())
                    .latitude(partnerRegisterRequest.getLatitude())
                    .longitude(partnerRegisterRequest.getLongitude())
                    .sundayOpen(partnerRegisterRequest.isSundayOpen())
                    .build();

            if (partnerRegisterRequest.getSectors() != null && !partnerRegisterRequest.getSectors().isEmpty()) {
                for (String sector: partnerRegisterRequest.getSectors()) {
                    Optional<Sector> sectorOptional = sectorService.findByCode(sector);
                    if (sectorOptional.isEmpty()) continue;
                    ((Partner) user).getSectors().add(sectorOptional.get());
                }
            }

            List<WorkingTimesDTO> workingTimes = partnerRegisterRequest.getWorkingTimes();
            if (workingTimes != null && !workingTimes.isEmpty()) {
                List<WorkingTimes> workingTimesList = new ArrayList<>();
                for (WorkingTimesDTO workingTime: workingTimes) {
                    WorkingTimes wt = modelMapper.map(workingTime, WorkingTimes.class);
                    wt.setPartner((Partner) user);
                    workingTimesList.add(wt);
                }
                ((Partner) user).setWorkingTimes(workingTimesList);
            }

            AddressDTO address = partnerRegisterRequest.getAddress();
            if (address != null) {
                Address save = addressFacade.save(address);
                ((Partner) user).setAddress(save);
            }
        }

        if (user == null) {
            throw new IllegalArgumentException("Kullanıcı bilgileri doldurulamadı.");
        }

        userDao.save(user);

        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken,request.getUsername(),request.getRole());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        var user = userDao.findUserByUid(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken,user.getUsername(),user.getRole());
    }
}