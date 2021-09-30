package com.tsystem.logisticsbe.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "driver")
public class DriverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "personal_number")
    private String personamNumber;

    @Column(name = "hours_worked")
    private Integer hoursWorked;

    @JoinColumn(name = "driver_status_id")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private DriverStatusEntity driverStatus;

    @JoinColumn(name = "current_city_id")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private CityEntity city;

    @JoinColumn(name = "current_truck_id")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private TruckEntity truck;

    @ManyToMany(mappedBy = "drivers")
    private List<RequestEntity> request;
}

