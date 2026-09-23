package com.hotel;

import com.hotel.entities.Usuario;
import com.hotel.repositories.UsuarioRepository;
import com.hotel.services.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioService usuarioService, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        
        // 1. CREAR CLIENTE/HUÉSPED DE PRUEBA (Mediante el Service)
        if (!usuarioRepository.existsByUsername("usuarioPrueba")) {
            Usuario cliente = new Usuario();
            cliente.setUsername("usuarioPrueba");
            cliente.setPassword("usuario123");
            
            // Al usar 'registrar', el Service le asignará el rol CLIENTE 
            // y además creará su correspondiente Huésped automáticamente.
            usuarioService.registrarHuesped(cliente);
            System.out.println(" Cliente y Huésped 'usuarioPrueba' creados con éxito");
        } else {
            System.out.println("️ El cliente 'usuarioPrueba' ya existe");
        }

        // 2. CREAR EMPLEADO DE PRUEBA 
        if (!usuarioRepository.existsByUsername("empleadoPrueba")) {
            Usuario empleado = new Usuario();
            empleado.setUsername("empleadoPrueba");
            
          
            
            // Usamos el repositorio directamente para evitar que el Service lo convierta en cliente
            usuarioService.registrarEmpleado(empleado);
            System.out.println("Empleado 'empleadoPrueba' creado con éxito");
        } else {
            System.out.println("️ El empleado 'empleadoPrueba' ya existe.");
        }
    }
}
