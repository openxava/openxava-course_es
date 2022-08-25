package com.tuempresa.facturacion.modelo;

import java.time.*;

import javax.persistence.*;

import org.openxava.annotations.*;

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
//Alternativa validacion por validator, en i18n debe ser 
//pedido_debe_estar_entregado=Pedido {0}/{1} debe estar entregado para ser a�adido a una Factura
@EntityValidator(
value=com.tuempresa.facturacion.validadores.ValidadorEntregadoParaEstarEnFactura.class,
properties= {
  @PropertyValue(name="anyo"),
  @PropertyValue(name="numero"),
  @PropertyValue(name="factura"),
  @PropertyValue(name="entregado")
})
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
    
//Alternativa de validaci�n retrollamada JPA
//    @PrePersist @PreUpdate // Antes de crear o modificar
//    private void validar() throws Exception {
//        if (factura != null && !isEntregado()) { // La l�gica de validaci�n
//            // La excepci�n de validaci�n del entorno Bean Validation
//            throw new javax.validation.ValidationException(
//                XavaResources.getString( // Para leer un mensaje i18n
//                    "pedido_debe_estar_entregado",
//                    getAnyo(),
//                    getNumero())
//            );
//        }
//    }
    
//Alternativa de validaci�n setter
//    public void setFactura(Factura factura) {
//        if (factura != null && !isEntregado()) { // La l�gica de validaci�n
//            // La excepci�n de validaci�n del entorno Bean Validation
//            throw new javax.validation.ValidationException(
//                XavaResources.getString( // Para leer un mensaje i18n
//                    "pedido_debe_estar_entregado",
//                    getAnyo(),
//                    getNumero())
//            );
//        }
//        this.factura = factura; // La asignaci�n t�pica del setter
//    }
    
//Alternativa de validaci�n Bean Validation, en i18n debe ser 
//pedido_debe_estar_entregado=Pedido {anyo}/{numero} debe estar entregado para ser a�adido a una Factura
//    @AssertTrue(  // Antes de grabar confirma que el m�todo devuelve true, si no lanza una excepci�n
//    	    message="pedido_debe_estar_entregado" // Mensaje de error en caso retorne false
//    	)
//    	private boolean isEntregadoParaEstarEnFactura() { // ...
//    	    return factura == null || isEntregado(); // La l�gica de validaci�n
//    	}
    
}
