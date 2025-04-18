package edu.comillas.icai.gitt.pat.spring.p5.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TODO#7
 * Añade 2 tests unitarios adicionales que validen diferentes casos
 * (no variaciones del mismo caso) de registro con datos inválidos
 */

class RegisterRequestUnitTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidRequest() {
        // Given ...
        RegisterRequest registro = new RegisterRequest(
                "Nombre", "nombre@email.com",
                Role.USER, "aaaaaaA1");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        assertTrue(violations.isEmpty());
    }


    @Test
    public void testInvalidName(){
        // Given ... -> preparar un objeto (tipo RegisterRequest) con datos de entrada
        RegisterRequest registro = new RegisterRequest(
                "", "nombre@email.com",
                Role.USER, "aaaaaaA1");
        // When ... -> se ejecuta la validación teniendo en cuenta las restricciones de la clase RegisterRequest
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ... -> comprobar resultado esperado
        assertEquals(1, violations.size()); // Solo hay 1 error de validación
    }

    @Test
    public void testInvalidEmail(){
        // Given ... -> preparar un objeto (tipo RegisterRequest) con datos de entrada
        RegisterRequest registro = new RegisterRequest(
                "Nombre", "mail_incorrecto",
                Role.USER, "aaaaaaA1");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        assertEquals(1, violations.size());
    }

}