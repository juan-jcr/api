package com.rest.api.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Clase de utilidad para la generación y validación de tokens JWT.
 */
@Component
public class JwtUtils {
    /**
     * Clave secreta para firmar y verificar los tokens JWT.
     */
    @Value("${security.jwt.key.private}")
    private String SECRET_KEY;

    /**
     * Tiempo de expiración del token en milisegundos se establece 30 minutos.
     */
    private static final long EXPIRATION_TIME = 1800000;

    /**
     * Genera un token JWT.
     *
     * @param username el nombre de usuario que se incluirá en el token.
     * @return devuelve un token JWT generado.
     */
    public String generateToken(String username) {
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }
    /**
     * Valida un token JWT.
     * @param token el token JWT que se desea validar.
     * @return el nombre de usuario contenido en el token si es válido de lo contrario un null
     */
    public String validateToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET_KEY))
                    .build()
                    .verify(token);
            return decodedJWT.getClaim("username").asString();
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
