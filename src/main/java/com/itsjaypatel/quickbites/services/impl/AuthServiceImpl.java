package com.itsjaypatel.quickbites.services.impl;

import com.itsjaypatel.quickbites.exceptions.SignUpException;
import com.itsjaypatel.quickbites.dtos.LoginRequest;
import com.itsjaypatel.quickbites.dtos.LoginResponse;
import com.itsjaypatel.quickbites.dtos.SignUpRequest;
import com.itsjaypatel.quickbites.dtos.SignUpResponse;
import com.itsjaypatel.quickbites.entities.Cart;
import com.itsjaypatel.quickbites.entities.Customer;
import com.itsjaypatel.quickbites.entities.UserEntity;
import com.itsjaypatel.quickbites.entities.Wallet;
import com.itsjaypatel.quickbites.repositories.CartRepository;
import com.itsjaypatel.quickbites.repositories.CustomerRepository;
import com.itsjaypatel.quickbites.repositories.UserRepository;
import com.itsjaypatel.quickbites.services.AuthService;
import com.itsjaypatel.quickbites.services.WalletService;
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

import static com.itsjaypatel.quickbites.enums.Role.CUSTOMER;


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