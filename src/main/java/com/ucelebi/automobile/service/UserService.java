package com.ucelebi.automobile.service;

import com.ucelebi.automobile.model.User;

import java.util.Optional;

public interface UserService extends AbstractService<User, Long> {
    Optional<User> findUserByUidAndPassword(String uid, String password);
    Optional<User> findUserByUid(String uid);
}
