package com.ucelebi.automobile.util;

import com.ucelebi.automobile.exception.PermissionException;
import com.ucelebi.automobile.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ApplicationUtils {

    public static User getCurrentUser() throws PermissionException{

        Authentication authentication = SecurityContextHolder.getContext() != null ? SecurityContextHolder.getContext().getAuthentication() : null;

        if (authentication == null || authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof User currentUser)) {
            throw new PermissionException("Logged in user not found.");
        }
        return currentUser;
    }
}
