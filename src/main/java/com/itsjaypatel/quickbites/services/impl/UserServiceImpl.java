package com.itsjaypatel.quickbites.services.impl;

import com.itsjaypatel.quickbites.entities.UserEntity;
import com.itsjaypatel.quickbites.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserEntity currentLoggedUser(){
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
