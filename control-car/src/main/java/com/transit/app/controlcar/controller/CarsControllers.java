package com.transit.app.controlcar.controller;

import com.transit.app.controlcar.data.Placa;
import com.transit.app.controlcar.model.Cars;
import org.springframework.web.bind.annotation.*;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value="/carApp")
public class CarsControllers {

    /**
     * Atributos
     */
    private List<Cars> cars= new ArrayList();
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateFormat dateFormat = new SimpleDateFormat ("HH:mm:ss");
    String mananaInicio = "07:00:00";
    String mananaFin = "09:30:00";
    String tardeInicio = "16:00:00";
    String tardeFin = "19:30:00";

    /**
     * Constructor
     */
    CarsControllers(){

        this.cars=allCars();
    }

    /**
     * Obtiene los datos de todos vehiculos
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Cars> getAllCars(){
        return this.cars;
    }

    // Data del vehiculo
    List<Cars> allCars(){
        List<Cars> carList = new ArrayList<>();

        Cars model1=addCar(1L,"PCD-XlS-0001",new Date());
        Cars model2=addCar(2L,"WED-XlS-0003",new Date());
        Cars model3=addCar(3L,"ACD-XlS-0005",new Date());
        Cars model4=addCar(4L,"XCD-XlS-0007",new Date());
        Cars model5=addCar(5L,"LGF-XlS-0009",new Date());
        carList.add(model1);
        carList.add(model2);
        carList.add(model3);
        carList.add(model4);
        carList.add(model5);
        return carList;
    }

    /**
     * Asignando el tipo de valor a los atributos del modelo de datos
     * @param id
     * @param plateNumer
     * @param actuallyDate
     * @return
     */
    Cars addCar(Long id, String plateNumer, Date actuallyDate) {
        Cars car = new Cars();
        car.setId(id);
        car.setPlateNumber(plateNumer);
        car.setActuallyDate(actuallyDate);

        return car;
    }

    /**
     * Recupera la informacion en formato json, a partir de un valor enviado
     * @param plateNum
     * @return
     */
    @RequestMapping(value = "/{plateNumber}", method = RequestMethod.GET)
    public Cars getPlate(@PathVariable("plateNumber") String plateNum) {

        return this.cars.stream().filter(cars-> cars.getPlateNumber().toUpperCase().equals(plateNum.toUpperCase())).findFirst().orElse(null);
    }

    /**
     * Validar la informacion ingresada en en la app
     * @param plateNum
     * @param date
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/{plateNumber}/{toDate}", method = RequestMethod.GET)
    public String getPlate(@PathVariable("plateNumber") String plateNum, @PathVariable("toDate") String date) throws ParseException {
        Cars modelcar = new Cars();
        //car = this.cars.stream().filter(cars-> cars.getPlateNumber().toUpperCase().equals(plateNum.toUpperCase())).findFirst().orElse(null);
        String mensaje = "";

        String convertido = fechaHora.format(new Date());
        Date dateAct = sdf.parse(convertido);
        Date dateReg = sdf.parse(date);
        //Placa picoPlaca=new Placaeor();

        if(dateAct.compareTo(dateReg)>0){
            mensaje = "Fecha Anterior "+ dateReg;
            return mensaje;
        }else {
            modelcar = this.cars.stream().filter(cars-> cars.getPlateNumber().toUpperCase().equals(plateNum.toUpperCase())).findFirst().orElse(null);
            if (modelcar != null){
                boolean diasRest = diasRestriccion(modelcar, dateReg);
                if (diasRest){
                    mensaje = "El vehiculo de placas: "+ modelcar.getPlateNumber() +" puede circular normalmente en horario: "+ dateFormat.format(dateReg);
                }else {
                    mensaje = "El vehiculo de placas: "+ modelcar.getPlateNumber() +" no puede circular normalmente en horario: "+ dateFormat.format(dateReg);
                    if (time(mananaInicio, mananaFin, tardeInicio, tardeFin , dateReg)){
                        mensaje = "";
                        mensaje = "Pico y placa no disponible en este horario: " + dateFormat.format(dateReg) + ". El vehiculo con placas: " +modelcar.getPlateNumber()
                                +" puede circular por este periodo.";
                    }
                }
            }else{
                mensaje = "No existe ninguna placa registrada.";
            }
        }
        return mensaje;
    }

    /**
     * Verificar el dia de pico y placa
     * @param getDay
     * @return
     */


    public String getPDay(Date getDay) {
        String[] arregloDias = new String[]{"DOMINGO","LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO"};
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(getDay);
        //return cal.get(Calendar.DAY_OF_WEEK);
        return arregloDias[cal.get(Calendar.DAY_OF_WEEK)-1];
    }

    /**
     * Validar el horario del pico y placa
     * @param manInici
     * @param manFin
     * @param tarInici
     * @param tarFin
     * @param dateVal
     * @return
     * @throws ParseException
     */

    public boolean time(String manInici, String manFin, String tarInici, String tarFin, Date dateVal) throws ParseException {
        boolean rango = false;
        DateFormat dateFormat = new SimpleDateFormat ("HH:mm:ss");
        String dateValidate;
        dateValidate = dateFormat.format(dateVal);
        if ((manInici.compareTo(dateValidate)> 0 || manFin.compareTo(dateValidate)< 0)
                && (tarInici.compareTo(dateValidate)> 0 || tarFin.compareTo(dateValidate)< 0)) {
            rango = true;

        }
        return rango;
    }

    /**
     * Verificar el dia de pico y placa
     * @param modelcar
     * @param date
     * @return
     */

    public boolean diasRestriccion(Cars modelcar, Date date){
        boolean rest = false;
        if (modelcar.getPlateNumber().endsWith(String.valueOf(Placa.valueOf(getPDay(date)).getModeloPlaca1()))
                || modelcar.getPlateNumber().endsWith(String.valueOf(Placa.valueOf(getPDay(date)).getModeloPlaca2()))){
            rest = true;
        }
        return rest;
    }


}