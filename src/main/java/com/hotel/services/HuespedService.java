package com.hotel.services;

import com.hotel.entities.Huesped;
import com.hotel.repositories.HuespedRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HuespedService {

    private final HuespedRepository repo;

    public HuespedService(HuespedRepository repo) {
        this.repo = repo;
    }

    public Huesped crear(Huesped h) {
        return repo.save(h);
    }

    public Optional<Huesped> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public List<Huesped> listarTodos() {
        return repo.findAll();
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
