package edu.comillas.icai.gitt.pat.spring.p5.entity;

import edu.comillas.icai.gitt.pat.spring.p5.model.Role;
import jakarta.persistence.*;

/**
 * TODO#2
 * Completa la entidad AppUser (cuya tabla en BD se llamará APP_USER) -> TABLA: APP_USER
 * para que tenga los siguientes campos obligatorios:
 * - id, que será la clave primaria numérica y autogenerada
 * - email, que debe tener valores únicos en toda la tabla
 * - password
 * - role, modelado con la clase Role ya existente en el proyecto
 * - name
 */


@Entity
@Table(name = "APP_USERS") // tabla en BD se llamará APP_USER
public class AppUser {
    // id, que será la clave primaria numérica y autogenerada
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    // email, que debe tener valores únicos en toda la tabla
    @Column(nullable = false, unique = true)
    public String email;

    // password : no puede no haber contraseña
    @Column(nullable = false)
    public String password;

    // role, modelado con la clase Role : no puede no haber role
    @Column(nullable = false)
    public Role role;

    @Column(nullable = false)
    public String name;

}