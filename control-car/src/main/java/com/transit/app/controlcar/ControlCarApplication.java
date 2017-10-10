package com.transit.app.controlcar;

import com.transit.app.controlcar.model.Cars;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

//Punto de partida de una app
@SpringBootApplication
public class ControlCarApplication {
    public static HashMap<String,Cars> carsApp;

    public static void main(String[] args) {
		SpringApplication.run(ControlCarApplication.class, args);
	}
}
