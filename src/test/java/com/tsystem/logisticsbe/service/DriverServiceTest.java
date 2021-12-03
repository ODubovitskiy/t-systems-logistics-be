package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.dto.DriverPersonalAccountDTO;
import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.entity.domain.DriverStatus;
import com.tsystem.logisticsbe.entity.domain.TruckStatus;
import com.tsystem.logisticsbe.exception.ApiException;
import com.tsystem.logisticsbe.mapper.DriverMapper;
import com.tsystem.logisticsbe.mapper.TransportOrderMapper;
import com.tsystem.logisticsbe.repository.AppUserRepository;
import com.tsystem.logisticsbe.repository.CityRepository;
import com.tsystem.logisticsbe.repository.DriverRepository;
import com.tsystem.logisticsbe.repository.TruckRepository;
import com.tsystem.logisticsbe.security.AppUser;
import com.tsystem.logisticsbe.security.UserRoles;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class DriverServiceTest {

    private DriverService driverServiceUnderTest;

    @Mock
    private DriverRepository driverRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private TruckRepository truckRepository;
    @Mock
    private DriverMapper driverMapper;
    @Mock
    private TransportOrderMapper transportOrderMapper;
    @Mock
    private AppUserRepository appUserRepository;

    @BeforeEach
    void setUp() {
        driverServiceUnderTest = new DriverService(driverRepository, cityRepository, truckRepository,
                driverMapper, transportOrderMapper, appUserRepository);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void create() {
        Driver driver = new Driver(null, 1L, "Name", "LastName", "123", 10, DriverStatus.REST, new City(), null, null, null);
        driverServiceUnderTest.create(driver);
        ArgumentCaptor<Driver> argumentCaptor = ArgumentCaptor.forClass(Driver.class);
        verify(driverRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(driver);
    }

    @Test
    void getAll() {
        driverServiceUnderTest.getAll();
        verify(driverRepository).findByIsDeletedNull();
    }

    @Test
    void getById() {
        Driver driver = new Driver(null, 1L, "Name", "LastName", "123",
                10, DriverStatus.REST, new City(), null, null, null);

        given(driverRepository.findDriverByIdAndIsDeletedNull(driver.getId()))
                .willReturn(Optional.of(driver));

        DriverDTO result = driverServiceUnderTest.getById(driver.getId());
        assertThat(result).isEqualTo(driverMapper.mapToDTO(driver));
    }

    @Test
    void getById_Trows_Not_Found_Exception() {
        Long id = 1L;
        assertThatThrownBy(() -> driverServiceUnderTest.getById(id))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(String.format("Driver with id = %s doesn't exist.", id));
    }

    @Test
    void update_if_truck_and_city_stated() {
        Long id = 1L;
        City city = new City(1L, "Moscow", 1000);
        Truck truck = new Truck("Kamaz", 1L, "FF12345", 50f, 2, 10000, TruckStatus.WORKING, true, city, null);
        Driver driverToUpdate = new Driver(null, 1L, "Name", "LastName", "123",
                10, DriverStatus.DRIVING, null, null, null, null);

        Driver newDriver = new Driver(null, null, "Name", "LastName", "123",
                10, DriverStatus.DRIVING, city, truck, null, null);

        given(driverRepository.findDriverByIdAndIsDeletedNull(id)).willReturn(Optional.of(driverToUpdate));
        given(truckRepository.findById(truck.getId())).willReturn(Optional.of(truck));
        given(cityRepository.getById(city.getId())).willReturn(city);

        driverServiceUnderTest.update(id, newDriver);
        assertThat(driverToUpdate.getCity()).isEqualTo(newDriver.getCity());
        assertThat(driverToUpdate.getTruck()).isEqualTo(newDriver.getTruck());
    }

    @Test
    void update_if_truck_is_null() {
        Long id = 1L;
        City city = new City(1L, "Moscow", 1000);
        Truck truck = new Truck("Kamaz", 1L, "FF12345", 50f, 2, 10000, TruckStatus.WORKING, true, city, null);
        Driver driverToUpdate = new Driver(null, 1L, "Name", "LastName", "123",
                10, DriverStatus.DRIVING, null, null, null, null);

        Driver newDriver = new Driver(null, null, "Name", "LastName", "123",
                10, DriverStatus.DRIVING, city, null, null, null);

        given(driverRepository.findDriverByIdAndIsDeletedNull(id)).willReturn(Optional.of(driverToUpdate));
        given(cityRepository.getById(city.getId())).willReturn(city);

        driverServiceUnderTest.update(id, newDriver);
        assertThat(driverToUpdate.getCity()).isEqualTo(newDriver.getCity());
        assertThat(driverToUpdate.getTruck()).isEqualTo(null);
        assertThat(driverToUpdate.getStatus()).isEqualTo(DriverStatus.REST);
    }

    @Test
    void update_Trows_Driver_Not_Found_Exception() {
        Long id = 1L;
        City city = new City(1L, "Moscow", 1000);
        Truck truck = new Truck("Kamaz", 1L, "FF12345", 50f, 2, 10000, TruckStatus.WORKING, true, city, null);
        Driver driver = new Driver(null, 1L, "Name", "LastName", "123",
                10, DriverStatus.REST, city, truck, null, null);

        given(driverRepository.findDriverByIdAndIsDeletedNull(id)).willReturn(Optional.empty());
        assertThatThrownBy(() -> driverServiceUnderTest.update(id, driver))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(String.format("Driver with id = %s doesn't exist", id));
    }

    @Test
    void update_Trows_Truck_Not_Found_Exception() {
        Long id = 1L;
        City city = new City(1L, "Moscow", 1000);
        Truck truck = new Truck("Kamaz", 1L, "FF12345", 50f, 2, 10000, TruckStatus.WORKING, true, city, null);
        Driver driver = new Driver(null, 1L, "Name", "LastName", "123",
                10, DriverStatus.REST, city, truck, null, null);

        given(driverRepository.findDriverByIdAndIsDeletedNull(id)).willReturn(Optional.of(driver));
        given(truckRepository.findById(truck.getId())).willReturn(Optional.empty());
        given(cityRepository.getById(city.getId())).willReturn(city);

        assertThatThrownBy(() -> driverServiceUnderTest.update(id, driver))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(String.format("Truck with id = %s doesn't exist", id));
    }

    @Test
    void update_Trows_BAD_REQUEST_Exception() {
        Long id = 1L;
        City city = new City(1L, "Moscow", 1000);
        City city2 = new City(2L, "Sochi", 2000);
        Truck truck = new Truck("Kamaz", 1L, "FF12345", 50f, 2, 10000, TruckStatus.WORKING, true, city, null);
        Driver driver = new Driver(null, 1L, "Name", "LastName", "123",
                10, DriverStatus.REST, city, truck, null, null);

        given(driverRepository.findDriverByIdAndIsDeletedNull(id)).willReturn(Optional.of(driver));
        given(truckRepository.findById(truck.getId())).willReturn(Optional.of(truck));
        given(cityRepository.getById(city.getId())).willReturn(city2);

        assertThatThrownBy(() -> driverServiceUnderTest.update(id, driver))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Truck and driver are located in different cities");
    }


    @Test
    void delete() {
        Driver driver = new Driver(null, 1L, "Name", "LastName", "123",
                10, DriverStatus.REST, new City(), null, null, null);

        given(driverRepository.findDriverByIdAndIsDeletedNull(driver.getId()))
                .willReturn(Optional.of(driver));

        Long result = driverServiceUnderTest.delete(driver.getId());
        assertThat(result).isEqualTo(driver.getId());
    }

    @Test
    void delete_Trows_Not_Found_Exception() {
        Long id = 1L;
        assertThatThrownBy(() -> driverServiceUnderTest.delete(id))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(String.format("Driver with id = %s doesn't exist", id));
        verify(driverRepository, never()).delete(any());
    }

    @Test
    @Disabled
    void getDriverByPersonalNumber() {
        Driver driver = new Driver(null, 1L, "Name", "LastName", "123",
                10, DriverStatus.REST, new City(), null, null, null);

        AppUser appUser = new AppUser(1L, "name", "lastName", "driver@gmail.com",
                "password", UserRoles.DRIVER);

        given(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .willReturn(appUser);

        given(driverRepository.getDriverByAppUserId(appUser.getId()))
                .willReturn(Optional.of(driver));
        DriverPersonalAccountDTO driverByPersonalNumber = driverServiceUnderTest.getDriverByAppUSer();

        assertThat(driverByPersonalNumber.getDriver()).isEqualTo(driver);

    }

    @Test
    void findDriversByCityId() {
        City city = new City(1L, "Moscow", 1000);

        driverServiceUnderTest.findDriversByCityId(city.getId());
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(driverRepository).findDriversByCityId(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(city.getId());
    }

    @Test
    void getDriverByAppUserId() {
        Long id = 1L;
        AppUser appUser = new AppUser(1L, "name", "lastName", "driver@gmail.com",
                "password", UserRoles.DRIVER);
        given(driverRepository.getDriverByAppUserId(id)).willReturn(Optional.of(new Driver()));
        driverServiceUnderTest.getDriverByAppUserId(appUser.getId());
        ArgumentCaptor<Long> appUserIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(driverRepository).getDriverByAppUserId(appUserIdArgumentCaptor.capture());
        assertThat(appUserIdArgumentCaptor.getValue()).isEqualTo(id);
    }

    @Test
    void getDriverByAppUserId_trows_Not_Found_Exception() {
        Long id = 1L;
        assertThatThrownBy(() -> driverServiceUnderTest.getDriverByAppUserId(id))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Driver not found");

    }
}