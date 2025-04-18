package edu.comillas.icai.gitt.pat.spring.p5.repository;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // Test de integración de bases de daots (JPA)
class RepositoryIntegrationTest {
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    AppUserRepository appUserRepository;

    /**
     * TODO#9
     * Completa este test de integración para que verifique
     * que los repositorios TokenRepository y AppUserRepository guardan
     * los datos correctamente, y las consultas por AppToken y por email
     * definidas respectivamente en ellos retornan el token y usuario guardados.
     */
    @Test void saveTest() {
        // Given ...
        AppUser user = new AppUser();
        Token token = new Token();
        // Crear user con datos válidos para poder verificar que se guarda bien la información
        user.email = "nombre@email.com";
        user.password = "aaaaaaA1";
        user.name = "Nombre";
        user.role = Role.USER;

        // Crear un token (válido) a partir del 'user'
        token.appUser = user;

        // When ...
        // Se almacena la información
        appUserRepository.save(user);
        tokenRepository.save(token);

        // Then ...
        // Verificar consulta de appUser  partir del email
        AppUser userFromDb = appUserRepository.findByEmail("nombre@email.com");
        Assertions.assertEquals("Nombre", userFromDb.name); // El nombre debe ser el introducido en 'user'

        // Verificamos que el token se puede recuperar por id
        Token tokenFromDb = tokenRepository.findById(token.id).orElse(null);
        if (tokenFromDb != null) {
            Assertions.assertEquals("nombre@email.com", tokenFromDb.appUser.email); // Verificar que la asocciación por email es correcta
        }

    }


    /**
     * TODO#10
     * Completa este test de integración para que verifique que
     * cuando se borra un usuario, automáticamente se borran sus tokens asociados.
     */
    @Test void deleteCascadeTest() {
        // Given ...
        AppUser user = new AppUser();
        Token token = new Token();
        // Crear user con datos válidos para poder verificar que se guarda bien la información
        user.email = "nombre@email.com";
        user.password = "aaaaaaA1";
        user.name = "Nombre";
        user.role = Role.USER;

        // Crear un token (válido) a partir del 'user'
        token.appUser = user;

        // Se almacena la información
        appUserRepository.save(user);
        tokenRepository.save(token);


        // When ...
        // Al eliminar el usuario:
        appUserRepository.delete(user);


        // Then ...
        Assertions.assertEquals(0, appUserRepository.count()); // Númeor de usuarios = 0
        Assertions.assertEquals(0, tokenRepository.count()); // Número de tokens asociados al 'user' = 0
    }
}