package com.tsystem.logisticsbe.entity;

import com.tsystem.logisticsbe.entity.domain.ShipmentStatus;
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
@Table(name = "shipment")
public class Shipment {

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ShipmentStatus status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private Integer weight;

}
