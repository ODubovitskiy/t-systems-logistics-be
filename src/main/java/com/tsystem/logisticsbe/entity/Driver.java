package com.tsystem.logisticsbe.entity;

import com.tsystem.logisticsbe.entity.domain.DriverStatus;
import com.tsystem.logisticsbe.security.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "personal_number")
    private String personalNumber;

    @Column(name = "hours_worked")
    private Integer hoursWorked;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DriverStatus status;

    @Column(name = "is_deleted")
    LocalDateTime isDeleted;

    @JoinColumn(name = "current_city_id")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private City city;

    @JoinColumn(name = "current_truck_id")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private Truck truck;

    @ManyToMany(mappedBy = "drivers")
    private List<TransportOrder> transportOrders;

    @JoinColumn(name = "app_user_id")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private AppUser appUser;
}

