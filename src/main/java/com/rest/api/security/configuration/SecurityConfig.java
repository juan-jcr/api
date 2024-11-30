package com.rest.api.security.configuration;

import com.rest.api.security.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 * Configuración de seguridad para la aplicación.
 *
 * <p>Define la configuración de seguridad utilizando Spring Security, incluyendo el manejo
 * de autenticación basada en JWT, la política de sesión, y los permisos de acceso para diferentes
 * endpoints.</p>
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    /**
     * Filtro personalizado para la autenticación JWT.
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configura la cadena de filtros de seguridad.
     *
     * @param httpSecurity objeto {@link HttpSecurity} para configurar las políticas de seguridad.
     * @return la cadena de filtros de seguridad configurada.
     * @throws Exception si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                // Deshabilitar protección contra CSRF (no necesaria para APIs REST)
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                // Configurar política de sesión sin estado
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configurar permisos de acceso para los endpoints
                .authorizeHttpRequests(http -> {
                    // Permitir acceso público a los endpoints de autenticación
                    http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    // Requerir autenticación para los endpoints de la API
                    http.requestMatchers("/api/**").authenticated();
                    // Requerir autenticación para cualquier otra solicitud
                    http.anyRequest().authenticated();
                })
                // Agregar el filtro de autenticación JWT antes del filtro estándar de autenticación
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    /**
     * Este bean se utiliza para encriptar las contraseñas
     *
     * @return una instancia de {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
