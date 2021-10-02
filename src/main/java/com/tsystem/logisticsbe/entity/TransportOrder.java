package com.tsystem.logisticsbe.entity;

import com.tsystem.logisticsbe.entity.domain.TransportOrderStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "transport_order")
public class TransportOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    @Builder.Default
    private String number = UUID
            .randomUUID()
            .toString()
            .substring(0, 5)
            .replaceAll("-", "");

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransportOrderStatus status;

    @OneToMany(mappedBy = "transportOrder",
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<WayPoint> wayPoints;

    @JoinColumn(name = "truck_id")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private Truck truck;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})

    @JoinTable(name = "transport_order_drivers", joinColumns = {@JoinColumn(name = "transport_order_id")},
            inverseJoinColumns = {@JoinColumn(name = "driver_id")})
    private List<Driver> drivers;

}
