package com.transit.app.controlcar.model;

import java.util.Date;

public class Cars {

    //Atributos de la app
    private Long id;
    private String plateNumber;
    private Date actuallyDate;

     //Getters, Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Date getActuallyDate() {
        return actuallyDate;
    }

    public void setActuallyDate(Date actuallyDate) {
        this.actuallyDate = actuallyDate;
    }

}

