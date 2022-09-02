package com.tuempresa.facturacion.modelo;

import java.time.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.openxava.annotations.*;
import org.openxava.util.*;

import lombok.*;

@Entity @Getter @Setter
@View(extendsView="super.DEFAULT", 
members=
    "diasEntregaEstimados, entregado;" + // A�ade entregado
    "factura { factura }"
)
@View( name="SinClienteNiFactura", // Una vista llamada SinClienteNiFactura
members=                       // que no incluye cliente ni factura
    "anyo, numero, fecha;" +   // Ideal para ser usada desde Factura
    "detalles;" +
    "observaciones"
)
@Tab(baseCondition = "eliminado = false")
@Tab(name="Eliminado", baseCondition = "eliminado = true")
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
    
    @Column(columnDefinition="BOOLEAN DEFAULT FALSE")
    boolean entregado;
    
    //Alternativa de validaci�n Bean Validation, en i18n debe ser 
    //pedido_debe_estar_entregado=Pedido {anyo}/{numero} debe estar entregado para ser a�adido a una Factura
    @AssertTrue(  // Antes de grabar confirma que el m�todo devuelve true, si no lanza una excepci�n
    	    message="pedido_debe_estar_entregado" // Mensaje de error en caso retorne false
    	)
    	private boolean isEntregadoParaEstarEnFactura() { // ...
    	    return factura == null || isEntregado(); // La l�gica de validaci�n
    	}
    
    //Alternativ a de validacion al borrar con retrollamada
    @PreRemove
    private void validarPreBorrar() { // Ahora este m�todo no se ejecuta
        if (factura != null) { // autom�ticamente ya que el borrado real no se produce
            throw new javax.validation.ValidationException(
                XavaResources.getString("no_puede_borrar_pedido_con_factura"));
        }
    }
    
    
    public void setEliminado(boolean eliminado) {
        if (eliminado) validarPreBorrar(); // Llamamos a la validaci�n expl�citamente
        super.setEliminado(eliminado);
    }
    
}
