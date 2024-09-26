package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.entities.UserEntity;

public interface UserService {

    UserEntity getUserById(Long id);
}
