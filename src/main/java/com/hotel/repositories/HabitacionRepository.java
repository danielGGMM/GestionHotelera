package com.hotel.repositories;

import com.hotel.entities.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
}
