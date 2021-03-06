package com.tsystem.logisticsbe.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsystem.logisticsbe.entity.domain.TruckStatus;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "truck")
@ToString
public class Truck {

    @Column(name = "model")
    private String model;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reg_number", nullable = false, unique = true)
    private String regNumber;

    @Column(name = "average_Speed")
    private Float averageSpeed;

    @Column(name = "driver_shift_size")
    private Integer driverShiftSize;

    @Column(name = "load_capacity")
    private Integer loadCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TruckStatus status;

    @Column(name = "is_available")
    @ColumnDefault("TRUE")
    private boolean isAvailable = true;

    @JoinColumn(name = "current_city_id")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private City currentCity;

    @Column(name = "is_deleted")
    LocalDateTime isDeleted;

}
