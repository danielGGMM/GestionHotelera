package com.hotel.config;

import com.hotel.repositories.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioRepository usuarioRepo;

    public SecurityConfig(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    // 1. REGLAS DE ACCESO — qué rutas requieren qué rol
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // simplificado para nuestra API REST
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index.html", "/css/**", "/js/**").permitAll()
                .requestMatchers("/api/usuarios/registro").permitAll()
                .requestMatchers("/api/usuarios/**").hasRole("EMPLEADO")
                .requestMatchers("/api/habitaciones/**", "/api/huespedes/**").hasRole("EMPLEADO")
                .requestMatchers("/api/reservas/**").hasAnyRole("EMPLEADO", "CLIENTE")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/index.html")
                .permitAll()
            )
            .httpBasic(customizer -> {}); // Añadida la autenticación básica

        return http.build();
    }

    // 2. CÓMO CARGAR UN USUARIO DESDE LA BD
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            com.hotel.entities.Usuario u = usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            return new User(
                u.getUsername(),
                u.getPassword(),
                List.of(new SimpleGrantedAuthority(u.getRol()))
            );
        };
    }

    // 3. EL ENCODER — disponible como Bean para toda la app
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
