package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.dto.AuthRequestDTO;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.mapper.DriverMapper;
import com.tsystem.logisticsbe.repository.AppUserRepository;
import com.tsystem.logisticsbe.security.AppUser;
import com.tsystem.logisticsbe.security.JWTTokenProvider;
import com.tsystem.logisticsbe.security.UserRoles;
import com.tsystem.logisticsbe.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

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
    private final DriverService driverService;
    private final DriverMapper driverMapper;

    @Autowired
    public AuthRestController(AuthenticationManager authenticationManager, AppUserRepository appUserRepository, JWTTokenProvider jwtTokenProvider, DriverService driverService, DriverMapper driverMapper) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.driverService = driverService;
        this.driverMapper = driverMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            AppUser appUser = appUserRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
            String token = jwtTokenProvider.createToken(request.getEmail(), appUser.getRole().name());
            HashMap<String, Object> response = new HashMap<>();

            Driver driver = null;
            if (appUser.getRole() == UserRoles.DRIVER) {
                driver = driverService.getDriverByAppUSerId(appUser.getId());
            }

            // TODO: 13.11.2021
//            ObjectMapper objectMapper = new ObjectMapper();
//            String s = null;
//            try {
//                s = objectMapper.writeValueAsString(driver);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }

//            Cookie cookie = new Cookie("token", "this_is_my_token");
//            cookie.setPath("/");
//            cookie.setSecure(true);
//            cookie.setHttpOnly(true);
//            response.addCookie(cookie);

            response.put("email", request.getEmail());
            response.put("driver", driverMapper.mapToDTO(driver));
            response.put("token", token);

            return ResponseEntity.ok().body(response);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(@RequestBody HttpServletRequest request, @RequestBody HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
