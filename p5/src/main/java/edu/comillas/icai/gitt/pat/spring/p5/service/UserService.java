package edu.comillas.icai.gitt.pat.spring.p5.service;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileRequest;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileResponse;
import edu.comillas.icai.gitt.pat.spring.p5.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.p5.repository.TokenRepository;
import edu.comillas.icai.gitt.pat.spring.p5.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * TODO#6
 * Completa los métodos del servicio para que cumplan con el contrato
 * especificado en el interface UserServiceInterface, utilizando
 * los repositorios y entidades creados anteriormente
 */

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private AppUserRepository appUserRepository; // es necesario para poder utilizar los métodos de AppUserRepository
    @Autowired
    private TokenRepository tokenRepository; // es necesario para poder utilizar los métodos de TokenRepository


    /**
     * @param email email proporcionado para el login
     * @param password password proporcionado para el login
     * @return si las credenciales del usuario son correctas, retorna un token de sesión asociado a dicho usuario;
     * si las credenciales son incorrectas, retorna null
     */
    @Override
    public Token login(String email, String password) {
        //System.out.println("Login con email: '" + email + "' y contraseña: '" + password + "'");

        AppUser appUser = appUserRepository.findByEmail(email);

        if ((appUser == null) || (!Objects.equals(appUser.password, password))) return null; // si las credenciales son incorrectas, retorna null
        //System.out.println("ussuario encontrado: '" + appUser.email + "' y contraseña en BD: " + appUser.password);

        Token token = tokenRepository.findByAppUser(appUser);
        if (token != null) return token; // @return si las credenciales del usuario son correctas, retorna un token de sesión asociado a dicho usuario;

        // Crear nuevo token si no existe uno previo
        Token token_nuevo = new Token();
        token_nuevo.appUser = appUser;
        tokenRepository.save(token_nuevo); // guardarlo
        return token_nuevo;
    }


    /**
     * @param tokenId token de la sesión actual del usuario
     * @return si la sesión está creada (el token existe en BD), retorna el usuario asociado a dicha sesión;
     * si la sesión no existe, retorna null
     */
    @Override
    public AppUser authentication(String tokenId) {
        Optional<Token> tokenOpt = tokenRepository.findById(tokenId);

        if (tokenOpt.isPresent()) {
            Token token = tokenOpt.get();
            return token.appUser;
        } else {
            return null;
        }
    }


    /**
     * @param appUser usuario
     * @return respuesta con el perfil de dicho usuario
     */
    @Override
    public ProfileResponse profile(AppUser appUser) {
        return new ProfileResponse(appUser.name, appUser.email, appUser.role); // respuesta con el perfil de dicho usuario
    }


    /**
     * @param appUser usuario
     * @param profile nuevos datos para el perfil del usuario
     * @return respuesta con el perfil de dicho usuario actualizado
     */
    @Override
    public ProfileResponse profile(AppUser appUser, ProfileRequest profile) {

        if ((profile.name() != null) && (!profile.name().isEmpty())) {
            appUser.name = profile.name();
        }

        if ((Objects.equals(appUser.name, profile.name())) && (!Objects.equals(appUser.password, profile.password()))) {
            appUser.password = profile.password(); // Si el nombre coincide y se cambia la contraseña, esta se debe actualizar
        }

        appUserRepository.save(appUser); // Se actualiza el usuario con el cambio realizado
        return profile(appUser);
    }


    /**
     *
     * @param register datos de registro del usuario
     * @return respuesta con el perfil del nuevo usuario:
     */
    @Override
    public ProfileResponse profile(RegisterRequest register) {
        // register tiene todos los datos del usuario
        AppUser usuario = new AppUser();
        usuario.name = register.name();
        usuario.email = register.email();
        usuario.password = register.password();
        usuario.role = register.role();
        appUserRepository.save(usuario);

        return profile(usuario); // respuesta con el perfil del nuevo usuario
    }


    /**
     * @param tokenId token de la sesión actual del usuario para cerrarla
     */
    @Override
    public void logout(String tokenId) {
        tokenRepository.deleteById(tokenId); // eliminar la sesión actual = cerrar la sesión
    }


    /**
     * @param appUser usuario a borrar o dar de baja definitivamente
     */
    @Override
    public void delete(AppUser appUser) {
        appUserRepository.delete(appUser); // eliminar el usuario
    }

}
