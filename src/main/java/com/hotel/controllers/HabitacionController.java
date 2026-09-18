package com.hotel.controllers;

import com.hotel.entities.Habitacion;
import com.hotel.services.HabitacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    private final HabitacionService service;

    public HabitacionController(HabitacionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Habitacion> crear(@Valid @RequestBody Habitacion h) {
        Habitacion creada = service.crear(h);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> obtener(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Habitacion> listar() {
        return service.listarTodas();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
        @PutMapping("/{id}")
    public ResponseEntity<Habitacion>actualizar(@PathVariable Long id,@Valid@RequestBody Habitacion h) {
        
        java.util.Optional<Habitacion> resultado = service.buscarPorId(id);

        if (resultado.isPresent()) {
            h.setId(id);
            Habitacion actualizada = service.crear(h);
            return ResponseEntity.ok(actualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
}
