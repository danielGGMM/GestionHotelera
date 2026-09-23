package com.hotel.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;




@Entity
public class Usuario {
@OneToOne
private Huesped huesped;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @Column(unique = true)   //  no puede haber dos usuarios con el mismo username
    private String username;
    
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)


    @NotBlank
    private String password; //  guardar hasheada, NUNCA en texto plano

    @NotBlank
    private String rol;      //Cliente o Empleado

    // Constructor vacío (obligatorio para JPA)
    public Usuario() {}

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
}
