package com.ucelebi.automobile.service.impl;

import com.ucelebi.automobile.dao.UserDao;
import com.ucelebi.automobile.model.User;
import com.ucelebi.automobile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User, Long> implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        super(userDao);
        this.userDao = userDao;
    }


    @Override
    public Optional<User> findUserByUidAndPassword(String uid, String password) {
        return userDao.findUserByUidAndPassword(uid, password);
    }

    @Override
    public Optional<User> findUserByUid(String uid) {
        return userDao.findUserByUid(uid);
    }
}
