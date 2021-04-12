package ru.grishchenko.mymarket.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.grishchenko.mymarket.beans.JwtTokenUtil;
import ru.grishchenko.mymarket.dto.JwtRequest;
import ru.grishchenko.mymarket.dto.JwtResponse;
import ru.grishchenko.mymarket.dto.UserDto;
import ru.grishchenko.mymarket.dto.UserInfoDto;
import ru.grishchenko.mymarket.exception_handling.MyMarketError;
import ru.grishchenko.mymarket.services.CartService;
import ru.grishchenko.mymarket.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CartService cartService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new MyMarketError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);

        cartService.getCartForUser(authRequest.getUsername(), authRequest.getCartUuid());

        return ResponseEntity.ok(new JwtResponse(token));
    }
    @GetMapping("/alias")
    public UserInfoDto getUserName(Principal principal) {
        return new UserInfoDto(userService.getAliasByUserName(principal.getName()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            userService.registerNewUser(userDto);
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new MyMarketError(HttpStatus.BAD_REQUEST.value(), "Username or Email already exists"), HttpStatus.BAD_REQUEST);
        }


    }
}