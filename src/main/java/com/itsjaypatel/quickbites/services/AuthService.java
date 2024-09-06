package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.dtos.LoginRequest;
import com.itsjaypatel.quickbites.dtos.LoginResponse;
import com.itsjaypatel.quickbites.dtos.SignUpRequest;
import com.itsjaypatel.quickbites.dtos.SignUpResponse;


public interface AuthService {

    SignUpResponse signup(SignUpRequest request);

    LoginResponse login(LoginRequest request);

    LoginResponse refresh(String refreshToken);

    void logout();
}
