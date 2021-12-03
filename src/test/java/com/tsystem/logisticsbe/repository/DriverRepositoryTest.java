package com.tsystem.logisticsbe.repository;

import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.entity.domain.DriverStatus;
import com.tsystem.logisticsbe.entity.domain.TruckStatus;
import com.tsystem.logisticsbe.security.AppUser;
import com.tsystem.logisticsbe.security.UserRoles;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DriverRepositoryTest {

    @Autowired
    DriverRepository driverRepositoryUnderTest;
    @Autowired
    TruckRepository truckRepository;
    @Autowired
    CityRepository cityRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        driverRepositoryUnderTest.deleteAll();
        truckRepository.deleteAll();
        cityRepository.deleteAll();
    }

    @Test
    void findByIsDeletedNull() {
        Driver driver = new Driver(null, 1L, "John", "John", "123", 20, DriverStatus.REST, new City(), null, null, null);
        Driver driver2 = new Driver(LocalDateTime.now(), 2L, "John 2", "John", "456", 20, DriverStatus.REST, new City(), null, null, null);

        driverRepositoryUnderTest.saveAll(List.of(driver, driver2));

        List<Driver> result = driverRepositoryUnderTest.findByIsDeletedNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getPersonalNumber()).isEqualTo(driver.getPersonalNumber());
    }

    @Test
    void getDriversForOrder() {
        Truck truck = new Truck("Kamaz", 1L, "FF12345", 50f, 2, 10000, TruckStatus.WORKING, true, null, null);
        City city = new City(1L, "Moscow", 500);
        Driver driver = new Driver(null, 1L, "John", "John", "123", 20, DriverStatus.DRIVING, city, truck, null, null);
        Driver driver2 = new Driver(LocalDateTime.now(), 2L, "John 2", "John", "456", 20, DriverStatus.ON_SHIFT, new City(), truck, null, null);
        Driver driver3 = new Driver(null, 3L, "John 3", "John", "789", 0, DriverStatus.REST, city, null, null, null);
        Integer travelTime = (int) (city.getDistance() / truck.getAverageSpeed()) ;

        driverRepositoryUnderTest.saveAll(List.of(driver, driver2, driver3));

        HashSet<Driver> result = driverRepositoryUnderTest.getDriversForOrder(travelTime, city);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.contains(driver3)).isTrue();
    }

    @Test
    void getAllByTruckId() {
        Truck truck = new Truck("Kamaz", 1L, "FF12345", 50f, 2, 10000, TruckStatus.WORKING, true, null, null);
        Driver driver = new Driver(null, 1L, "John", "John", "123", 20, DriverStatus.DRIVING, new City(), truck, null, null);
        Driver driver2 = new Driver(LocalDateTime.now(), 2L, "John 2", "John", "456", 20, DriverStatus.ON_SHIFT, new City(), truck, null, null);
        Driver driver3 = new Driver(LocalDateTime.now(), 3L, "John 3", "John", "789", 20, DriverStatus.REST, new City(), null, null, null);

        driverRepositoryUnderTest.saveAllAndFlush(List.of(driver, driver2, driver3));

        List<Driver> result = driverRepositoryUnderTest.getAllByTruckId(truck.getId());
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void getDriverByPersonalNumberAndIsDeletedNull() {

        Driver driver = new Driver(null, 1L, "John", "John", "123", 20, DriverStatus.DRIVING, new City(), null, null, null);
        Driver driver2 = new Driver(LocalDateTime.now(), 2L, "John 2", "John", "456", 20, DriverStatus.ON_SHIFT, new City(), null, null, null);
        Driver driver3 = new Driver(LocalDateTime.now(), 3L, "John 3", "John", "789", 20, DriverStatus.REST, new City(), null, null, null);

        driverRepositoryUnderTest.saveAll(List.of(driver, driver2, driver3));

        Driver result;
        Optional<Driver> driverOptional = driverRepositoryUnderTest.getDriverByPersonalNumberAndIsDeletedNull("123");
        if (driverOptional.isPresent()) {
            result = driverOptional.get();
            assertThat(result.getIsDeleted()).isNull();
            assertThat(result.getPersonalNumber()).isEqualTo(driver.getPersonalNumber());
        }
    }

    @Test
    void getDriversByTruck() {
        Truck truck = new Truck("Kamaz", 1L, "FF12345", 50f, 2, 10000, TruckStatus.WORKING, true, null, null);
        Driver driver = new Driver(null, 1L, "John", "John", "123", 20, DriverStatus.DRIVING, new City(), truck, null, null);
        Driver driver2 = new Driver(LocalDateTime.now(), 2L, "John 2", "John", "456", 20, DriverStatus.ON_SHIFT, new City(), truck, null, null);
        Driver driver3 = new Driver(LocalDateTime.now(), 3L, "John 3", "John", "789", 20, DriverStatus.REST, new City(), null, null, null);

        driverRepositoryUnderTest.saveAll(List.of(driver, driver2, driver3));

        Set<Driver> result = driverRepositoryUnderTest.getDriversByTruck(truck);
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findDriversByCityId() {
        City city = new City(1L, "Moscow", 1000);

        Driver driver = new Driver(null, 1L, "John", "John", "123", 20, DriverStatus.DRIVING, city, null, null, null);
        Driver driver2 = new Driver(LocalDateTime.now(), 2L, "John 2", "John", "456", 20, DriverStatus.ON_SHIFT, new City(), null, null, null);

        driverRepositoryUnderTest.saveAll(List.of(driver, driver2));

        Set<Driver> result = driverRepositoryUnderTest.findDriversByCityId(1L);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void findDriverByIdAndIsDeletedNull() {
        Driver driver = new Driver(null, 1L, "John", "John", "123", 20, DriverStatus.DRIVING, new City(), null, null, null);
        Driver driver2 = new Driver(LocalDateTime.now(), 2L, "John 2", "John", "456", 20, DriverStatus.ON_SHIFT, new City(), null, null, null);

        driverRepositoryUnderTest.saveAll(List.of(driver, driver2));

        Driver result;
        Optional<Driver> driverOptional = driverRepositoryUnderTest.findDriverByIdAndIsDeletedNull(1L);
        if (driverOptional.isPresent()) {
            result = driverOptional.get();
            assertThat(result.getId()).isEqualTo(driver.getId());
        }
    }

    @Test
    void getDriverByAppUserId() {
        AppUser appUser = new AppUser(1L, "name", "lastName", "email@mail.com", "pass", UserRoles.DRIVER);
        Driver driver = new Driver(null, 1L, "John", "John", "123", 20, DriverStatus.DRIVING, new City(), null, null, appUser);
        Driver driver2 = new Driver(LocalDateTime.now(), 2L, "John 2", "John", "456", 20, DriverStatus.ON_SHIFT, new City(), null, null, null);

        driverRepositoryUnderTest.saveAll(List.of(driver, driver2));

        Driver result;
        Optional<Driver> driverOptional = driverRepositoryUnderTest.getDriverByAppUserId(1L);
        if (driverOptional.isPresent()) {
            result = driverOptional.get();
            assertThat(result.getPersonalNumber()).isEqualTo(driver.getPersonalNumber());
        }
    }
}