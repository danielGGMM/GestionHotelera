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
        // Validar que el huésped y la habitación existen si se pasan referencias
        if (r.getHuesped() != null && r.getHuesped().getId() != null) {
            Huesped h = huespedRepo.findById(r.getHuesped().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Huésped no encontrado"));
            r.setHuesped(h);
        }

        if (r.getHabitacion() != null && r.getHabitacion().getId() != null) {
            Habitacion hab = habitacionRepo.findById(r.getHabitacion().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada"));
            r.setHabitacion(hab);
        }

        // Calcular precio total si hay fechas y habitación
        if (r.getFechaEntrada() != null && r.getFechaSalida() != null && r.getHabitacion() != null && r.getHabitacion().getPrecioPorNoche() != null) {
            long noches = ChronoUnit.DAYS.between(r.getFechaEntrada(), r.getFechaSalida());
            if (noches <= 0) {
                throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada");
            }
            r.setPrecioTotal(noches * r.getHabitacion().getPrecioPorNoche());
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
