package com.ucelebi.automobile.auth;

import com.ucelebi.automobile.enums.Role;
import com.ucelebi.automobile.enums.UserType;

/**
 * User: ucelebi
 * Date: 24.06.2023
 * Time: 11:46
 */
public class CustomerRegisterRequest extends RegisterRequest{
    public CustomerRegisterRequest() {}

    public CustomerRegisterRequest(String name,
                                   String displayName,
                                   String username,
                                   String password,
                                   Role role,
                                   String phoneNumber,
                                   String mail,
                                   UserType userType) {
        super(name, displayName, username, password, role, phoneNumber, mail, userType);
    }
}
