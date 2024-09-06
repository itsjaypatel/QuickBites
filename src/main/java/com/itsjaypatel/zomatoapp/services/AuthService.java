package com.itsjaypatel.zomatoapp.services;

import com.itsjaypatel.zomatoapp.dtos.LoginRequest;
import com.itsjaypatel.zomatoapp.dtos.LoginResponse;
import com.itsjaypatel.zomatoapp.dtos.SignUpRequest;
import com.itsjaypatel.zomatoapp.dtos.SignUpResponse;


public interface AuthService {

    SignUpResponse signup(SignUpRequest request);

    LoginResponse login(LoginRequest request);

    LoginResponse refresh(String refreshToken);

    void logout();
}
