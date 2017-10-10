package com.transit.app.controlcar.data;

public enum  Placa{

    LUNES(1,2),
    MARTES (3,4),
    MIERCOLES (5,6),
    JUEVES(7,8),
    VIERNES(9,0);

    private int modeloPlaca1;
    private int modeloPlaca2;

    //Constructor

    private Placa(int modeloPlaca1, int modeloPlaca2) {
        this.modeloPlaca1 = modeloPlaca1;
        this.modeloPlaca2 = modeloPlaca2;
    }

    //Getters

    public int getModeloPlaca1() {
        return modeloPlaca1;
    }

    public int getModeloPlaca2() {
        return modeloPlaca2;
    }
}