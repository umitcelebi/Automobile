package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends AbstractItemDao<User, Long> {
    Optional<User> findUserByUidAndPassword(String uid, String password);
    Optional<User> findUserByUid(String uid);
}
