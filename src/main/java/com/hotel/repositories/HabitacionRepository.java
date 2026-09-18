package com.hotel.repositories;

import com.hotel.entities.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link Habitacion}.
 * Spring generates all CRUD operations automatically at runtime.
 */
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    // No hay que escribir nada: save, findById, findAll, deleteById... vienen gratis.
}
