package com.hotel.repositories;

import com.hotel.entities.Huesped;
import com.hotel.entities.Reserva;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface HuespedRepository extends JpaRepository<Huesped, Long> {
        boolean existsByUsuario_Id(Long id);
        
     @Query("SELECT r FROM Reserva r JOIN r.habitaciones h WHERE h.id = "
             + ":habitacionId AND r.fechaEntrada < :salida AND r.fechaSalida > :entrada")  
List<Reserva> findConflictos(Long habitacionId, LocalDate entrada, LocalDate salida);  


}
