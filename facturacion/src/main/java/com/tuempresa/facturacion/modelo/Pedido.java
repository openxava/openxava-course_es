package com.tuempresa.facturacion.modelo;

import java.time.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity @Getter @Setter
@View(extendsView="super.DEFAULT", 
members=
    "diasEntregaEstimados," + // AÑADE ESTA LÍNEA
    "factura { factura }"
)
@View( name="SinClienteNiFactura", // Una vista llamada SinClienteNiFactura
members=                       // que no incluye cliente ni factura
    "anyo, numero, fecha;" +   // Ideal para ser usada desde Factura
    "detalles;" +
    "observaciones"
)
public class Pedido extends DocumentoComercial{

    @ManyToOne
    @ReferenceView("SinClienteNiPedidos") // Esta vista se usa para visualizar factura
    private Factura factura;

    @Depends("fecha")
    public int getDiasEntregaEstimados() {
        if (getFecha().getDayOfYear() < 15) {
            return 20 - getFecha().getDayOfYear(); 
        }
        if (getFecha().getDayOfWeek() == DayOfWeek.SUNDAY) return 2;
        if (getFecha().getDayOfWeek() == DayOfWeek.SATURDAY) return 3;
        return 1;
    }
    
    @Column(columnDefinition="INTEGER DEFAULT 1")
    int diasEntrega;
    
    @PrePersist @PreUpdate 
    private void recalcularDiasEntrega() {
        setDiasEntrega(getDiasEntregaEstimados());
    }
    
}
