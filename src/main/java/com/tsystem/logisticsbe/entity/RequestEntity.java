package com.tsystem.logisticsbe.entity;

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
@Table(name = "request")
public class RequestEntity {

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

    @JoinColumn(name = "request_status_id")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private RequestStatusEntity requestStatus;

    @OneToMany(mappedBy = "request",
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<WayPointEntity> wayPoints;

    @JoinColumn(name = "truck_id")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private TruckEntity truck;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})

    @JoinTable(name = "requests_drivers", joinColumns = {@JoinColumn(name = "request_id")},
            inverseJoinColumns = {@JoinColumn(name = "driver_id")})
    private List<DriverEntity> drivers;

}
