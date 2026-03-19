package com.example.demo.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="user_uid", referencedColumnName = "firebase_uid")
    @JsonIgnore
    private User user; 

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String comuna;

    @Column(nullable = false)
    private String street;      // calle

    @Column(nullable = false)
    private String number;      //nro domicilio

    private String apartment;       // se puede dejar nulo la parte del apartamento

    @Column(name ="is_default")
    private boolean isDefault;      //Dejar dirección por defecto como percado libre

    //La dirección puede usarse en multiples ordenes tipo informe historico?
    @OneToMany(mappedBy = "address")
    @JsonIgnore
    private List<Order> orders;

}
