package com.itsjaypatel.zomatoapp.services.impl;

import com.itsjaypatel.zomatoapp.dtos.LoginRequest;
import com.itsjaypatel.zomatoapp.dtos.LoginResponse;
import com.itsjaypatel.zomatoapp.dtos.SignUpRequest;
import com.itsjaypatel.zomatoapp.dtos.SignUpResponse;
import com.itsjaypatel.zomatoapp.entities.Cart;
import com.itsjaypatel.zomatoapp.entities.Customer;
import com.itsjaypatel.zomatoapp.entities.UserEntity;
import com.itsjaypatel.zomatoapp.entities.Wallet;
import com.itsjaypatel.zomatoapp.exceptions.SignUpException;
import com.itsjaypatel.zomatoapp.repositories.CartRepository;
import com.itsjaypatel.zomatoapp.repositories.CustomerRepository;
import com.itsjaypatel.zomatoapp.repositories.UserRepository;
import com.itsjaypatel.zomatoapp.services.AuthService;
import com.itsjaypatel.zomatoapp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.itsjaypatel.zomatoapp.enums.Role.CUSTOMER;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CartRepository cartRepository;
    private final WalletService walletService;

    @Override
    public SignUpResponse signup(SignUpRequest request) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            throw new SignUpException("User already exists with email " + request.getEmail());
        }
        UserEntity user = userRepository.save(
                UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(CUSTOMER)).build()
        );
        Customer customer = customerRepository.save(
                Customer
                .builder()
                .location(modelMapper.map(request.getAddress(), Point.class))
                .user(user).build());

        Wallet wallet = walletService.createWallet(user);

        Cart cart = cartRepository.save(
                Cart.builder().customer(customer).cartItems(Set.of()).build()
        );

        return modelMapper.map(user,SignUpResponse.class);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                        request.getPassword()));
        UserEntity user = (UserEntity) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new LoginResponse(accessToken,refreshToken);
    }

    @Override
    public LoginResponse refresh(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        UserEntity user = userRepository.findById(userId).get();

        String accessToken = jwtService.generateAccessToken(user);
        return new LoginResponse(accessToken,refreshToken);
    }

    @Override
    public void logout() {

    }
}
