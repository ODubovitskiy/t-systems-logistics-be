package com.tsystem.logisticsbe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tsystem.logisticsbe.entity.domain.DriverStatus;
import com.tsystem.logisticsbe.security.AppUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "driver")
public class Driver {

    @Column(name = "is_deleted")
    LocalDateTime isDeleted;
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
    @JoinColumn(name = "current_city_id")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private City city;

    @JoinColumn(name = "current_truck_id")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private Truck truck;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "current_order_id")
    @JsonBackReference
    private TransportOrder transportOrder;

    @JoinColumn(name = "app_user_id")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private AppUser appUser;

}

