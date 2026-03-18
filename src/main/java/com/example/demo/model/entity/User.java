package com.example.demo.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.model.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {
    //PK desde firebase Realizar logica para recuperar usuario desde firebase
    @Id
    @Column(name="firebase_uid")
    private String firebaseUid;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    //Rol será enum ya que los roles serán pocos y no modificables
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;      //Cliente,  Admin

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    //Relaciones
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List <Address> addresses;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
