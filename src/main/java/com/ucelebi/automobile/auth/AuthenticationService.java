package com.ucelebi.automobile.auth;

import com.ucelebi.automobile.dao.UserDao;
import com.ucelebi.automobile.enums.UserType;
import com.ucelebi.automobile.exception.AlreadyExistException;
import com.ucelebi.automobile.model.Customer;
import com.ucelebi.automobile.model.Partner;
import com.ucelebi.automobile.model.Sector;
import com.ucelebi.automobile.model.User;
import com.ucelebi.automobile.security.JwtService;
import com.ucelebi.automobile.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserDao userDao;
    private final SectorService sectorService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserDao userDao, SectorService sectorService, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userDao = userDao;
        this.sectorService = sectorService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> byUsername = userDao.findUserByUid(request.getUsername());
        if (byUsername.isPresent()) {
            throw new AlreadyExistException("Kullanıcı sistemde mevcut");
        }

        User user = null;
        if (request.getUserType().equals(UserType.BIREYSEL)) {
            user = new Customer.builder()
                    .uid(request.getUsername())
                    .name(request.getName())
                    .displayName(request.getName())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phoneNumber(request.getPhoneNumber())
                    .role(request.getRole())
                    .build();

        } else if (request.getUserType().equals(UserType.KURUMSAL)) {
            user = new Partner.builder()
                    .uid(request.getUsername())
                    .name(request.getName())
                    .displayName(request.getDisplayName())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phoneNumber(request.getPhoneNumber())
                    .role(request.getRole())
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .sundayOpen(request.isSundayOpen())
                    .build();

            if (request.getSectors() != null && !request.getSectors().isEmpty()) {
                for (String sector: request.getSectors()) {
                    Optional<Sector> sectorOptional = sectorService.findByCode(sector);
                    if (sectorOptional.isEmpty()) continue;
                    ((Partner) user).getSectors().add(sectorOptional.get());
                }
            }

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