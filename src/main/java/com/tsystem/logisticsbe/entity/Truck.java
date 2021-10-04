package com.tsystem.logisticsbe.entity;

import com.tsystem.logisticsbe.entity.domain.TruckStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "truck")
public class Truck {

    @Column(name = "model")
    private String model;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reg_number")
    private String regNumber;

    @Column(name = "driver_shift_size")
    private Integer driverShiftSize;

    @Column(name = "load_capacity")
    private Integer loadCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TruckStatus status;

    @JoinColumn(name = "current_city_id")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private City currentCity;

}
