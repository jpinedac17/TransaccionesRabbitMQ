package com.consumer.model;

public class Referencias {
    private String factura;
    private String codigoInterno;

    // Constructor vacio (lo requiere asi jackson para mapear el json)
    public Referencias() {
    }

    //Getters y setters
    public String getFactura() {
        return factura;
    }
    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }
    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }
}