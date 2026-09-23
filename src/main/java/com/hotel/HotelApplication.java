package com.hotel;

import com.hotel.entities.Huesped;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelApplication {

	public static void main(String[] args) {
        System.out.println("Bienvenido a la gestión de Hoteles");
		SpringApplication.run(HotelApplication.class, args);
               
	}

}
