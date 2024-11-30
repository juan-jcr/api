package com.rest.api.service;

import com.rest.api.exception.AlreadyExistsException;
import com.rest.api.exception.UnauthorizedException;
import com.rest.api.persistence.entity.UserEntity;
import com.rest.api.persistence.repository.IUserRepository;
import com.rest.api.presentation.dto.TokenResponseDTO;
import com.rest.api.presentation.dto.UserDTO;
import com.rest.api.security.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio para manejar la lógica de negocio relacionada con registro e inicio de sesión.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param userDto A {@link UserDTO} Objeto que contiene la información del usuario para el registro.
     * @return Mensaje de registro con exito {@link AlreadyExistsException} lanza un error si ya existe un usuario
     * @throws AlreadyExistsException error si existe un usuario con el nombre de usuario proporcionado
     */
    public String register(UserDTO userDto) {
        /**
         *Intenta encontrar un usuario con el nombre de usuario en la base de datos, si se encuentra produce una exception AlreadyExistsException
         */
        Optional<UserEntity> findUser = userRepository.findByUsername(userDto.getUsername());
        if(findUser.isPresent()){
            throw new AlreadyExistsException("Usuario ya existe");
        }
        /**
         * Crea un nuevo objeto UserEntity y establece el nombre de usuario y contraseña a partir del UserDTO.
         * Se codifica la contraseña mediante passwordEncoder.
         */
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        /**
         * Guarda el objeto UserEntity en la base de datos.
         */
        userRepository.save(userEntity);
        /**
         * Genera un token JWT utilizando jwtUtils con el nombre de usuario del usuario registrado.
         */
        String token = jwtUtils.generateToken(userEntity.getUsername());
        /**
         * Devuelve un mensaje de éxito.
         */
        return "Usuario registrado con éxito";
    }

    /**
     * Autentica a un usuario y genera un token JWT.
     *
     * @param userDto Un objeto {@link UserDTO} que contiene las credenciales del usuario.
     * @return Un objeto {@link TokenResponseDTO} que contiene el token JWT.
     * @throws UnauthorizedException si las credenciales proporcionadas no son válidas.
     */
    public TokenResponseDTO login(UserDTO userDto) {
        Optional<UserEntity> user = userRepository.findByUsername(userDto.getUsername());

        if (user.isPresent() && passwordEncoder.matches(userDto.getPassword(), user.get().getPassword())) {
            String token = jwtUtils.generateToken(user.get().getUsername());
            return new TokenResponseDTO(token);
        }

        throw new UnauthorizedException("Credenciales inválidas");
    }
}
