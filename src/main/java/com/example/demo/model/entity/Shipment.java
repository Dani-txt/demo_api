package com.example.demo.model.entity;

import java.time.LocalDateTime;

import com.example.demo.model.enums.ShipmentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="order_id")
    private Order order;

    //Simulación de codigo de seguimiento
    @Column(name="tracking_number", unique=true)
    private String trackingNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private ShipmentStatus status;  //Preparando, en transito, etc

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt; //fecha y hora de la actualización del pedido
}
