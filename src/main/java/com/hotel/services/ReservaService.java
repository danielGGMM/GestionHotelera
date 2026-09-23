package com.hotel.services;

import com.hotel.entities.Habitacion;
import com.hotel.entities.Huesped;
import com.hotel.entities.Reserva;
import com.hotel.repositories.HabitacionRepository;
import com.hotel.repositories.HuespedRepository;
import com.hotel.repositories.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepo;
    private final HuespedRepository huespedRepo;
    private final HabitacionRepository habitacionRepo;

    public ReservaService(ReservaRepository reservaRepo, HuespedRepository huespedRepo, HabitacionRepository habitacionRepo) {
        this.reservaRepo = reservaRepo;
        this.huespedRepo = huespedRepo;
        this.habitacionRepo = habitacionRepo;
    }

    public Reserva crear(Reserva r) {
        // Validar que el huésped existe si se pasa referencia
        if (r.getHuesped() != null && r.getHuesped().getId() != null) {
            Huesped h = huespedRepo.findById(r.getHuesped().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Huésped no encontrado"));
            r.setHuesped(h);
        }

        // Validar que todas las habitaciones existen
        if (r.getHabitaciones() != null && !r.getHabitaciones().isEmpty()) {
            List<Habitacion> habitacionesValidadas = new java.util.ArrayList<>();
            for (Habitacion habReq : r.getHabitaciones()) {
                if (habReq.getId() != null) {
                    Habitacion habBd = habitacionRepo.findById(habReq.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Habitación con ID " + habReq.getId() + " no encontrada"));
                    habitacionesValidadas.add(habBd);
                }
            }
            r.setHabitaciones(habitacionesValidadas);
        } else {
             throw new IllegalArgumentException("La reserva debe tener al menos una habitación");
        }

        // Calcular precio total sumando los precios de todas las habitaciones
        if (r.getFechaEntrada() != null && r.getFechaSalida() != null) {
            long noches = ChronoUnit.DAYS.between(r.getFechaEntrada(), r.getFechaSalida());
            if (noches <= 0) {
                throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada");
            }
            
            double precioTotalPorNoche = 0.0;
            for (Habitacion hab : r.getHabitaciones()) {
                if (hab.getPrecioPorNoche() != null) {
                    precioTotalPorNoche += hab.getPrecioPorNoche();
                }
            }
            r.setPrecioTotal(noches * precioTotalPorNoche);
        }

        if (r.getEstado() == null) {
            r.setEstado("CONFIRMADA");
        }

        return reservaRepo.save(r);
    }

    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepo.findById(id);
    }

    public List<Reserva> listarTodas() {
        return reservaRepo.findAll();
    }

    public void eliminar(Long id) {
        reservaRepo.deleteById(id);
    }
}
