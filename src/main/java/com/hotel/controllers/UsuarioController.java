package com.hotel.controllers;

import com.hotel.entities.Usuario;
import com.hotel.services.UsuarioService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@Valid @RequestBody Usuario u) {
        Usuario creado = service.registrarHuesped(u);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
    @GetMapping("/{Username}")
    public ResponseEntity<Usuario> obtener (@PathVariable String Username ){
            Usuario usuario = service.buscarPorUsername(Username);
                    return ResponseEntity.ok(usuario);

}
    @GetMapping
     public List<Usuario> listar() {
        return service.listarTodos();
    }
}
