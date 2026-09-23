package com.hotel.services;

import com.hotel.entities.Huesped;
import com.hotel.entities.Usuario;
import com.hotel.repositories.HuespedRepository;
import com.hotel.repositories.UsuarioRepository;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

        private final HuespedRepository HuespedRepo ;

    private final UsuarioRepository Usuariorepo;
    //private final BCryptPasswordEncoder encoder;

private final PasswordEncoder encoder; 

public UsuarioService(UsuarioRepository Usuariorepo, PasswordEncoder encoder,HuespedRepository Huespedrepo) {
    this.Usuariorepo = Usuariorepo;
    this.encoder = encoder; 
    this.HuespedRepo = Huespedrepo;
}


    public Usuario registrarHuesped(Usuario u) {
        if (Usuariorepo.existsByUsername(u.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        // Hashear la contraseña ANTES de guardarla
        u.setPassword(encoder.encode(u.getPassword()));
        //Para asegurarnos que cuando se crea el usuario se crea con rol cliente y no lo han malversado
        u.setRol("ROLE_CLIENTE");
    Usuario usuarioGuardado = Usuariorepo.save(u);
        Huesped huesped = new Huesped();
        huesped.setUsuario(usuarioGuardado);
        huesped.setNombre(usuarioGuardado.getUsername()); // Usamos su username como nombre temporal
huesped.setDni("PENDIENTE");                      // Texto provisional obligado por el @NotBlank
huesped.setEmail("pendiente@hotel.com"); 
           HuespedRepo.save(huesped);
    return usuarioGuardado;
    }

    
    public Usuario registrarEmpleado(Usuario u) {
        if (Usuariorepo.existsByUsername(u.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        // Hashear la contraseña ANTES de guardarla
        u.setPassword(encoder.encode(u.getPassword()));
        //Para asegurarnos que cuando se crea el usuario se crea con rol cliente y no lo han malversado
        u.setRol("ROLE_EMPLEADO");
    Usuario usuarioGuardado = Usuariorepo.save(u);
        Huesped huesped = new Huesped();
        huesped.setUsuario(usuarioGuardado);
        huesped.setNombre(usuarioGuardado.getUsername()); // Usamos su username como nombre temporal
huesped.setDni("PENDIENTE");                      // Texto provisional obligado por el @NotBlank
huesped.setEmail("pendiente@hotel.com"); 
           HuespedRepo.save(huesped);
    return usuarioGuardado;
    }
    public Usuario buscarPorUsername(String username) {
        return Usuariorepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }
    
    public List<Usuario> listarTodos(){
    return Usuariorepo.findAll();}
}
