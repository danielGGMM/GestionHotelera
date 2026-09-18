package com.hotel.repositories;

import com.hotel.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link Reserva}.
 * Spring generates all CRUD operations automatically at runtime.
 */
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // No hay que escribir nada: save, findById, findAll, deleteById... vienen gratis.
}
