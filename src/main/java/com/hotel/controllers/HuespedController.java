package com.hotel.controllers;

import com.hotel.entities.Huesped;
import com.hotel.services.HuespedService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/huespedes")
public class HuespedController {

    private final HuespedService service;

    public HuespedController(HuespedService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Huesped> crear(@Valid@RequestBody Huesped h) {
        Huesped creado = service.crear(h);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Huesped> obtener(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Huesped> listar() {
        return service.listarTodos();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
        @PutMapping("/{id}")
    public ResponseEntity<Huesped> actualizar(@Valid@PathVariable Long id, @RequestBody Huesped h) {
        
        java.util.Optional<Huesped> resultado = service.buscarPorId(id);

        if (resultado.isPresent()) {
            h.setId(id);
            Huesped actualizado = service.crear(h);
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
