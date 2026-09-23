package com.hotel.repositories;

import com.hotel.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
