package com.hotel.repositories;

import com.hotel.entities.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link Huesped}.
 * Spring generates all CRUD operations automatically at runtime.
 */
public interface HuespedRepository extends JpaRepository<Huesped, Long> {
    // No hay que escribir nada: save, findById, findAll, deleteById... vienen gratis.
}
