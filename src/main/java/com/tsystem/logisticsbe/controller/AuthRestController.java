package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.dto.AuthRequestDTO;
import com.tsystem.logisticsbe.exception.ApiException;
import com.tsystem.logisticsbe.repository.AppUserRepository;
import com.tsystem.logisticsbe.security.AppUser;
import com.tsystem.logisticsbe.security.JWTTokenProvider;
import com.tsystem.logisticsbe.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final JWTTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AuthRestController(AuthenticationManager authenticationManager, AppUserRepository appUserRepository,
                              JWTTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public HashMap<String, Object> authenticate(@RequestBody AuthRequestDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            AppUser appUser = appUserRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));

            String token = jwtTokenProvider.createToken(request.getEmail(), appUser.getRole().name());
            HashMap<String, Object> payload = new HashMap<>();
            userDetailsService.setCurrentUser(appUser, payload);

            payload.put("token", token);
            return payload;
        } catch (AuthenticationException e) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Invalid email or password");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.setClearAuthentication(true);
        securityContextLogoutHandler.logout(request, response, null);
        request.logout();
    }
}
