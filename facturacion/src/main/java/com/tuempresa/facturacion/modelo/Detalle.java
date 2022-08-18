package com.tuempresa.facturacion.modelo;
 
import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;
 
@Embeddable @Getter @Setter
public class Detalle {
 
    int cantidad;
 
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    Producto producto;
    
    @Money
    @Depends("producto.numero, cantidad") // Cuando usuario cambie producto o cantidad
    public BigDecimal getImporte() { // esta propiedad se recalculará y se redibujará
        if (producto == null || producto.getPrecio() == null) return BigDecimal.ZERO;
        return new BigDecimal(cantidad).multiply(producto.getPrecio());
    }
 
}