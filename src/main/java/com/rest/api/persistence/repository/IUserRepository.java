package com.rest.api.persistence.repository;

import com.rest.api.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
/**
 * Repositorio para realizar operaciones CRUD sobre la entidad User.
 */
public interface IUserRepository extends CrudRepository<UserEntity, Long> {
    /**
     * Encuentra por nombre de usuario.
     *
     * @param username Nombre de usuario.
     * @return Nombre de usuario.
     */
    Optional<UserEntity> findByUsername(String username);
}
