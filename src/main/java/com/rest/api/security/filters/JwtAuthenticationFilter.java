package com.rest.api.security.filters;

import com.rest.api.persistence.entity.UserEntity;
import com.rest.api.persistence.repository.IUserRepository;
import com.rest.api.security.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticación JWT que se ejecuta una vez por cada solicitud.
 *
 *Este filtro procesa cada solicitud HTTP para verificar si contiene un token JWT válido
 * en el encabezado de autorización. Si el token es válido, establece la autenticación.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /**
     * Utilidad para la validación de tokens JWT.
     */
    private final JwtUtils jwtUtils;
    /**
     * Repositorio para acceder a datos de los usuarios.
     */
    private final IUserRepository userRepository;

    /**
     * procesa las solicitudes HTTP para realizar la autenticación basada en JWT.
     *
     * Verifica si la solicitud contiene un encabezado de autorización con un token JWT válido.
     * Si el token es válido, autentica al usuario correspondiente y lo configura en el contexto de seguridad.
     *
     * @param request la solicitud HTTP entrante.
     * @param response la respuesta HTTP que se enviará.
     * @param filterChain la cadena de filtros que se debe continuar.
     * @throws ServletException si ocurre un error al procesar el filtro.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;
        // Validar si el encabezado contiene un token JWT
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtUtils.validateToken(jwt);
        }
        // Si el token es válido y no hay autenticación en el contexto de seguridad
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Buscar al usuario en la base de datos
            UserEntity userEntity = userRepository.findByUsername(username).orElse(null);

            if (userEntity != null) {
                // Crear un token de autenticación para el usuario
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Continuar con el siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }
}
