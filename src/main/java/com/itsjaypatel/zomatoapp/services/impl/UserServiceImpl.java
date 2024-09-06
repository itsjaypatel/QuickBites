package com.itsjaypatel.zomatoapp.services.impl;

import com.itsjaypatel.zomatoapp.entities.UserEntity;
import com.itsjaypatel.zomatoapp.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserEntity currentLoggedUser(){
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
