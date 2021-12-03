package com.tsystem.logisticsbe.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.entity.Manager;
import com.tsystem.logisticsbe.mapper.DriverMapper;
import com.tsystem.logisticsbe.mapper.ManagerMapper;
import com.tsystem.logisticsbe.repository.AppUserRepository;
import com.tsystem.logisticsbe.service.DriverService;
import com.tsystem.logisticsbe.service.api.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final DriverService driverService;
    private final DriverMapper driverMapper;
    private final ManagerMapper managerMapper;
    private final IManagerService managerService;

    @Autowired
    public UserDetailsServiceImpl(AppUserRepository appUserRepository, DriverService driverService, DriverMapper driverMapper, ManagerMapper managerMapper, IManagerService managerService) {
        this.appUserRepository = appUserRepository;
        this.driverService = driverService;
        this.driverMapper = driverMapper;
        this.managerMapper = managerMapper;
        this.managerService = managerService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email '%s' doesn't exist", email)));
        return SecurityUser.maptoUserDetails(appUser);
    }

    public void setCurrentUser(AppUser appUser, HashMap<String, Object> payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (appUser.getRole() == UserRoles.DRIVER) {
            Driver driver = driverService.getDriverByAppUserId(appUser.getId());
            try {

                String driverString = objectMapper.writeValueAsString(driverMapper.mapToDTO(driver));
                payload.put("currentUser", driverString);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else if (appUser.getRole() == UserRoles.MANAGER) {
            Manager manager = managerService.findManagerByAppUserId(appUser.getId());
            try {
                String managerString = objectMapper.writeValueAsString(managerMapper.mapToDTO(manager));
                payload.put("currentUser", managerString);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
