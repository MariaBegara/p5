# P5
Aplicación web que usa Spring JPA para persistir los datos de un API REST de gestión de usuarios.
El API permite el registro de nuevos usuarios y su identificación mediante email y password.
Una vez identificados, se emplea una cookie de sesión para autenticar las peticiones que permiten 
a los usuarios leer, modificar y borrar sus datos. También existe un endpoint para cerrar la sesión.  

## Endpoints

// TODO#1: rellena la tabla siguiente analizando el código del proyecto

| Método | Ruta                  | Descripción                                 | Respuestas                                           |
|--------|-----------------------|---------------------------------------------|------------------------------------------------------|
| POST   | /api/users            | Registrar un usuario nuevo                  | 201 Created (OK), 409 Conflict (email ya registrado) |
| POST   | /api/users/me/session | Iniciar sesión                              | 201 Created con cookie de sesión, 401 Unauthorized   |
| GET    | /api/users/me/session | Obtener perfil de un usuario autenticado    | 200 OK con datos del perfil, 401 Unauthorized        |
| PUT    | /api/users/me         | Actualizar perfil de un usuario autenticado | 200 OK con datos actualizados, 401 Unauthorized      |
| DELETE | /api/users/me         | Eliminar cuenta de un usuario autenticado   | 204 No Content, 401 Unauthorized                     |
| POST   | /api/logout           | Cerrar sesión                               | 204 No Content (logout correcto), 401 Unauthorized   |




## Comandos 

- Construcción: 
  ```sh
  ./mvnw clean package
  ```

- Ejecución: 
  ```sh
  ./mvnw spring-boot:run
  ```

- Tests:
  ```sh
  ./mvnw test
  ```
