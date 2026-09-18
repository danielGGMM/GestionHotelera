package com.hotel.services;

import com.hotel.entities.Habitacion;
import com.hotel.repositories.HabitacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitacionService {

    private final HabitacionRepository repo;

    public HabitacionService(HabitacionRepository repo) {
        this.repo = repo;
    }

    public Habitacion crear(Habitacion h) {
        if (h.getPrecioPorNoche() != null && h.getPrecioPorNoche() < 0) {
            throw new IllegalArgumentException("El precio por noche no puede ser negativo");
        }
        return repo.save(h);
    }

    public Optional<Habitacion> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public List<Habitacion> listarTodas() {
        return repo.findAll();
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
