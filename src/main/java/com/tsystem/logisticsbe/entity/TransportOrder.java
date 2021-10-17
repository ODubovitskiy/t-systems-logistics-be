package com.tsystem.logisticsbe.entity;

import com.tsystem.logisticsbe.entity.domain.TransportOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transport_order")
public class TransportOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransportOrderStatus status;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<WayPoint> wayPoints;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "transport_order_trucks", joinColumns = {@JoinColumn(name = "transport_order_id")},
            inverseJoinColumns = {@JoinColumn(name = "dtruck_id")})
    private List<Truck> trucks;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "transport_order_drivers", joinColumns = {@JoinColumn(name = "transport_order_id")},
            inverseJoinColumns = {@JoinColumn(name = "driver_id")})
    private List<Driver> drivers;

}
